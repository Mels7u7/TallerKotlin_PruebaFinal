package com.example.tallerkotlin_final_everis.network

data class FeedResponse (
    val id: String,
    val user_id: String,
    val username:String,
    val user_image:String,
    val body:String,
    val image:String,
    val likes:Int,
    val comment: List<Comment>
)