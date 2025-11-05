package com.example.android_14_compose_app.api

import com.example.android_14_compose_app.data.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatsApi {
    @GET("cats")
    suspend fun fetchCats(
        @Query("tag") tag: String
    ): Response<List<Cat>>
}