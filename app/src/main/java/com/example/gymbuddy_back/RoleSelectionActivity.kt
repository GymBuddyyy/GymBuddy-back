package com.example.gymbuddy_back

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gymbuddy_back.databinding.ActivityRoleSelectionBinding

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "트레이너" 버튼 클릭
        binding.trainerButton.setOnClickListener {
            navigateToSignUp("트레이너")
        }

        // "비기너" 버튼 클릭
        binding.beginnerButton.setOnClickListener {
            navigateToSignUp("비기너")
        }
    }

    private fun navigateToSignUp(role: String) {
        val intent = Intent(this, SignUpActivity::class.java)
        intent.putExtra("ROLE", role) // 선택한 role 데이터를 전달
        startActivity(intent)
    }
}
