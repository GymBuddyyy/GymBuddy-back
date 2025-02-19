package com.example.gymbuddy_back

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoLocalService {
    @GET("v2/local/search/keyword.json")
    suspend fun searchPlaces(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("x") longitude: Double?, // 경도
        @Query("y") latitude: Double?,  // 위도
        @Query("radius") radius: Int = 500 // 검색 반경 (기본 500m)
    ): Response<KakaoSearchResponse>
}
