package com.example.gymbuddy_back

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RegisterGymActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_gym)

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance()

        // UI 요소 초기화
        val etName = findViewById<EditText>(R.id.etName)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val etLatitude = findViewById<EditText>(R.id.etLatitude)
        val etLongitude = findViewById<EditText>(R.id.etLongitude)
        val etPhoneNumber = findViewById<EditText>(R.id.etPhoneNumber)
        val etUserId = findViewById<EditText>(R.id.etUserId)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        // 등록 버튼 클릭 이벤트
        btnSubmit.setOnClickListener {
            val name = etName.text.toString()
            val address = etAddress.text.toString()
            val latitude = etLatitude.text.toString().toDoubleOrNull()
            val longitude = etLongitude.text.toString().toDoubleOrNull()
            val phoneNumber = etPhoneNumber.text.toString()
            val userId = etUserId.text.toString().toLongOrNull()

            // 입력값 검증
            if (name.isBlank() || address.isBlank() || latitude == null || longitude == null || phoneNumber.isBlank() || userId == null) {
                Toast.makeText(this, "모든 필드를 정확히 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val gym = Gym(
                    id = System.currentTimeMillis(),
                    name = name,
                    address = address,
                    latitude = latitude,
                    longitude = longitude,
                    phoneNumber = phoneNumber,
                    userId = userId
                )

                // Firestore에 데이터 저장
                firestore.collection("gyms").add(gym)
                    .addOnSuccessListener {
                        Toast.makeText(this, "체육관 등록 성공!", Toast.LENGTH_SHORT).show()

                        // MainActivity로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish() // 현재 액티비티 종료
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "등록 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
