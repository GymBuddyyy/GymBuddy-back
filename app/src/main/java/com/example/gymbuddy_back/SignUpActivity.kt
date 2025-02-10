package com.example.gymbuddy_back

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymbuddy_back.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private val db = Firebase.firestore // Firestore 초기화
    private var role: String? = null // 선택된 Role 저장 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 인증 초기화
        mAuth = Firebase.auth

        // Role 데이터를 전달받음
        role = intent.getStringExtra("ROLE") ?: "비기너" // 기본값: "비기너"

        // NumberPicker (나이 선택) 초기화
        val agePicker: NumberPicker = findViewById(R.id.age_picker)
        agePicker.minValue = 17
        agePicker.maxValue = 100
        agePicker.wrapSelectorWheel = true

        // Spinner (성별 선택) 초기화
        val genderSpinner: Spinner = findViewById(R.id.gender_spinner)
        val genderOptions = arrayOf("남자", "여자")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // 회원가입 버튼 클릭 이벤트
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()
            val name = binding.nameEdit.text.toString().trim()
            val age = agePicker.value
            val gender = genderSpinner.selectedItem.toString()
            val address = binding.addressEdit.text.toString().trim()

            // 입력값 검증
            if (email.isBlank() || password.isBlank() || name.isBlank() || address.isBlank()) {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "유효한 이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "비밀번호는 최소 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase에 사용자 등록 및 Firestore에 정보 저장
            signUp(email, password, name, age, gender, address, role!!)
        }
    }

    private fun signUp(email: String, password: String, name: String, age: Int, gender: String, address: String, role: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val userId = user?.uid

                    // Firestore에 사용자 정보 저장
                    val userInfo = hashMapOf(
                        "id" to userId,
                        "email" to email,
                        "name" to name,
                        "age" to age,
                        "gender" to gender,
                        "address" to address,
                        "role" to role, // Role 저장
                        "enabled" to true // 기본 활성 상태
                    )

                    userId?.let {
                        db.collection("users").document(it)
                            .set(userInfo)
                            .addOnSuccessListener {
                                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
                                startActivity(intent)
                                finish() // 현재 액티비티 종료
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Firestore 저장 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    val errorMessage = task.exception?.message ?: "회원가입 실패"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }
}
