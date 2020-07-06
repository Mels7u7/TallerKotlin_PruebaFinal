package com.example.tallerkotlin_final_everis.network

import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("profile")
    suspend fun getProfile() :Response<UserResponse>
    @GET("posts")
    suspend fun getFeed():Response<List<FeedResponse>>
    @GET("users")
    suspend fun getFriends():Response<List<FriendResponse>>
}