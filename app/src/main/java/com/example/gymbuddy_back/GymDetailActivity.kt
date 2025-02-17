package com.example.gymbuddy_back

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GymDetailActivity : AppCompatActivity() {

    private lateinit var tvGymName: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvPhoneNumber: TextView

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gym_detail)

        // UI 요소 초기화
        tvGymName = findViewById(R.id.tvGymName)
        tvAddress = findViewById(R.id.tvAddress)
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber)

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance()

        // Intent에서 gymId 가져오기
        val gymId = intent.getStringExtra("gymId")
        if (gymId != null) {
            loadGymDetails(gymId)
        }
    }

    /**
     * 🔍 Firestore에서 선택한 체육관 정보 가져오기
     */
    private fun loadGymDetails(gymId: String) {
        firestore.collection("gyms").document(gymId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val gym = document.toObject(Gym::class.java)
                    gym?.let {
                        tvGymName.text = it.name
                        tvAddress.text = it.address
                        tvLatitude.text = it.latitude.toString()
                        tvLongitude.text = it.longitude.toString()
                        tvPhoneNumber.text = it.phoneNumber
                    }
                }
            }
            .addOnFailureListener {
                tvGymName.text = "데이터를 불러올 수 없습니다."
            }
    }
}
