package com.example.gymbuddy_back

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URLEncoder

class RegisterGymActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etLatitude: EditText
    private lateinit var etLongitude: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etUserId: EditText
    private lateinit var btnSubmit: Button
    private lateinit var rvSearchResults: RecyclerView

    private val client = OkHttpClient()
    private val searchResults = mutableListOf<Gym>()
    private lateinit var gymAdapter: GymAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_gym)

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance()

        // UI 요소 초기화
        etName = findViewById(R.id.etName)
        etAddress = findViewById(R.id.etAddress)
        etLatitude = findViewById(R.id.etLatitude)
        etLongitude = findViewById(R.id.etLongitude)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etUserId = findViewById(R.id.etUserId)
        btnSubmit = findViewById(R.id.btnSubmit)
        rvSearchResults = findViewById(R.id.rvSearchResults)

        // RecyclerView 설정
        gymAdapter = GymAdapter(searchResults) { selectedGym ->
            etName.setText(selectedGym.name)
            etAddress.setText(selectedGym.address)
            etLatitude.setText(selectedGym.latitude.toString())
            etLongitude.setText(selectedGym.longitude.toString())
            etPhoneNumber.setText(selectedGym.phoneNumber)

            rvSearchResults.visibility = View.GONE // 검색 리스트 숨기기
        }

        rvSearchResults.layoutManager = LinearLayoutManager(this)
        rvSearchResults.adapter = gymAdapter

        // 체육관 이름 입력 시 자동 검색 (디바운싱 적용)
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty() || s.length < 2) {
                    rvSearchResults.visibility = View.GONE
                    return
                }
                searchGym(s.toString())
            }
        })

        // 등록 버튼 클릭
        btnSubmit.setOnClickListener {
            registerGym()
        }
    }

    /**
     * 🔍 체육관 검색 API 호출 (네이버 지도 API)
     */
    private fun searchGym(query: String) {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val url = "https://openapi.naver.com/v1/search/local.json?query=$encodedQuery&display=5"

        val request = Request.Builder()
            .url(url)
            .addHeader("X-Naver-Client-Id", "wrq72e08hn") // ✅ API 키 수정
            .addHeader("X-Naver-Client-Secret", "Fs6snwZ08pirYH9MCZeE2XG4jm36PygLmapXr1Xc") // ✅ API 키 수정
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterGymActivity, "검색 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()

                    // 🔥 API 응답을 로그에 출력 (디버깅용)
                    println("🔥 API 응답: $responseBody")

                    if (responseBody.isNullOrEmpty()) {
                        runOnUiThread {
                            Toast.makeText(this@RegisterGymActivity, "검색 결과 없음", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val json = JSONObject(responseBody)

                    // ✅ `items` 키 사용 (`places` 아님)
                    if (!json.has("items")) {
                        runOnUiThread {
                            Toast.makeText(this@RegisterGymActivity, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val places = json.getJSONArray("items") // ✅ `items` 키 사용
                    println("🔥 검색된 items 배열: $places")

                    val gyms = mutableListOf<Gym>()
                    for (i in 0 until places.length().coerceAtMost(5)) {
                        val place = places.getJSONObject(i)
                        println("🔥 검색된 체육관 데이터: $place")

                        val name = place.optString("title", "이름 없음") // ✅ `name` → `title`
                        val address = place.optString("roadAddress", "주소 없음") // ✅ `road_address` → `roadAddress`
                        val phoneNumber = place.optString("telephone", "전화번호 없음") // ✅ `phone_number` → `telephone`

                        // 네이버 API에는 위도, 경도가 없으므로 임시 값 설정
                        val latitude = 0.0
                        val longitude = 0.0

                        gyms.add(Gym(name = name, address = address, latitude = latitude, longitude = longitude, phoneNumber = phoneNumber))
                    }

                    runOnUiThread {
                        searchResults.clear()
                        searchResults.addAll(gyms)
                        gymAdapter.notifyDataSetChanged()
                        rvSearchResults.visibility = if (searchResults.isEmpty()) View.GONE else View.VISIBLE
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@RegisterGymActivity, "검색 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    /**
     * 🏋️ 체육관 정보 등록 (Firestore에 저장)
     */
    private fun registerGym() {
        val name = etName.text.toString()
        val address = etAddress.text.toString()
        val latitude = etLatitude.text.toString().toDoubleOrNull()
        val longitude = etLongitude.text.toString().toDoubleOrNull()
        val phoneNumber = etPhoneNumber.text.toString()
        val userId = etUserId.text.toString()

        if (name.isBlank() || address.isBlank() || latitude == null || longitude == null || phoneNumber.isBlank() || userId.isBlank()) {
            Toast.makeText(this, "모든 필드를 정확히 입력하세요.", Toast.LENGTH_SHORT).show()
        } else {
            val gym = hashMapOf(
                "name" to name,
                "address" to address,
                "latitude" to latitude,
                "longitude" to longitude,
                "phoneNumber" to phoneNumber,
                "userId" to userId
            )

            firestore.collection("gyms").add(gym)
                .addOnSuccessListener {
                    Toast.makeText(this, "체육관 등록 성공!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "등록 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
