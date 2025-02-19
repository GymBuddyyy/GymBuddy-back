package com.example.gymbuddy_back

import com.google.gson.annotations.SerializedName

data class KakaoSearchResponse(
    @SerializedName("documents") val documents: List<KakaoGym>
)

data class KakaoGym(
    @SerializedName("id") val id: String, // 카카오 API에서는 Long 대신 String ID 제공
    @SerializedName("place_name") val name: String,
    @SerializedName("address_name") val address: String,
    @SerializedName("y") val latitude: Double,  // 위도
    @SerializedName("x") val longitude: Double, // 경도
    @SerializedName("phone") val phoneNumber: String?
) {
    // `Gym` 데이터 클래스로 변환
    fun toGym(userId: String): Gym {
        return Gym(
            id = id, // ✅ Firestore ID를 `String` 그대로 유지
            name = name,
            address = address,
            latitude = latitude,
            longitude = longitude,
            phoneNumber = phoneNumber ?: "", // 전화번호가 없을 경우 빈 문자열 처리
            userId = userId // ✅ `userId`도 `String`으로 유지
        )
    }

}
