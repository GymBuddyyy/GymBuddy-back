package com.example.gymbuddy_back

data class Gym(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val phoneNumber: String = "",
    val userId: String = "" // ✅ Firestore에서는 `userId`가 `String`이므로 타입 변경
) {
    // 매개변수가 없는 기본 생성자 (필요)
    constructor() : this("", "", "", 0.0, 0.0, "", "")
}
