package com.example.examen_final_android.network

import retrofit2.Response
import retrofit2.http.GET

interface Service {

    @GET("profile")
    suspend fun getProfile(): Response<UserResponse>

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET("users")
    suspend fun getUsers(): Response<List<UsersResponse>>

}