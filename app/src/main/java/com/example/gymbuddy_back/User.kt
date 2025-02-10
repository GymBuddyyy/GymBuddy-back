package com.example.gymbuddy_back

data class User(
    val id: String? = null,           // 사용자 고유 ID (Firebase Authentication UID)    --> 자동 생성
    val imageId: String? = null,      // 프로필 이미지 ID 또는 URL                        --> 나중에
    val name: String? = null,         // 이름
    val email: String? = null,        // 이메일
    val password: String? = null,     // 비밀번호 (보안을 위해 필요 시 제외)
    val age: Long? = null,            // 나이
    val gender: String? = null,       // 성별
    val address: String? = null,      // 주소
    val role: String? = null,         // 역할 (예: 관리자, 사용자)                         --> 회원가입 누르면 선택 후 나머지 저장
    val interested: String? = null,   // 관심사                                          --> 나중에
    val enabled: Boolean = true       // 계정 활성화 여부                                  --> 기본 true
)