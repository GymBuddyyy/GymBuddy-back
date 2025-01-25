package com.example.gymbuddy_back

data class Gym(
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val phoneNumber: String = "",
    val userId: Long = 0L
) {
    // 매개변수가 없는 기본 생성자 (필요)
    constructor() : this(0L, "", "", 0.0, 0.0, "", 0L)
}
