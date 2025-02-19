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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*

class RegisterGymActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var etName: EditText
    private lateinit var etAddress: EditText
    private lateinit var etLatitude: EditText
    private lateinit var etLongitude: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnSubmit: Button
    private lateinit var rvSearchResults: RecyclerView

    private val client = OkHttpClient()
    private val searchResults = mutableListOf<Gym>()
    private lateinit var gymAdapter: GymAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_gym)

        // Firebase 초기화
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance() // 🔹 FirebaseAuth 인스턴스 추가

        // UI 요소 초기화
        etName = findViewById(R.id.etName)
        etAddress = findViewById(R.id.etAddress)
        etLatitude = findViewById(R.id.etLatitude)
        etLongitude = findViewById(R.id.etLongitude)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
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
     * 🔍 체육관 검색 API 호출 (카카오 로컬 API)
     */
    private fun searchGym(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.instance.searchPlaces(
                    authorization = "KakaoAK eed00e1aa3c8b722ccdcf04938176e7b",
                    query = query,
                    longitude = null, // ✅ 위치 정보 사용 안 함
                    latitude = null   // ✅ 위치 정보 사용 안 함
                )

                if (response.isSuccessful) {
                    val gyms = response.body()?.documents
                        ?.take(5) // ✅ 최대 5개까지만 가져오기
                        ?.map { it.toGym(userId = auth.currentUser?.uid ?: "unknown") } ?: emptyList() // ✅ Firestore `userId`는 `String`이므로 Firebase UID 사용

                    withContext(Dispatchers.Main) {
                        searchResults.clear()
                        searchResults.addAll(gyms)
                        gymAdapter.notifyDataSetChanged()
                        rvSearchResults.visibility = if (searchResults.isEmpty()) View.GONE else View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterGymActivity, "검색 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
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

        // 🔹 로그인한 유저의 UID 가져오기
        val user = auth.currentUser
        val userId = user?.uid ?: "unknown" // 로그인되지 않았다면 "unknown" 저장

        if (name.isBlank() || address.isBlank() || latitude == null || longitude == null || phoneNumber.isBlank() || userId == "unknown") {
            Toast.makeText(this, "모든 필드를 정확히 입력하세요.", Toast.LENGTH_SHORT).show()
        } else {
            val gym = hashMapOf(
                "name" to name,
                "address" to address,
                "latitude" to latitude,
                "longitude" to longitude,
                "phoneNumber" to phoneNumber,
                "userId" to userId // ✅ 로그인한 유저의 UID 저장
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
