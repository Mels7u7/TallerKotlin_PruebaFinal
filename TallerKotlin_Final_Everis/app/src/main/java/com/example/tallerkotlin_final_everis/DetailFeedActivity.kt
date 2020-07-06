package com.example.tallerkotlin_final_everis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallerkotlin_final_everis.network.Comment
import com.example.tallerkotlin_final_everis.network.FeedResponse
import com.example.tallerkotlin_final_everis.network.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_feed_detail.*
import kotlinx.android.synthetic.main.item_feed_comments.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DetailFeedActivity : AppCompatActivity() , CommentsAdapter.CommentsHolder.OnAdapterListener{
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CommentsAdapter

    companion object {
        const val KEY_COMMENT = "comments"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)



        val mysharedP = mSharedPreferences(this)

        Log.e("mysharedP",mysharedP.getKey(KEY_COMMENT).toString())

        val gson = Gson()
        val itemType = object : TypeToken<List<Comment>>() {}.type
        val comments  = gson.fromJson<List<Comment>>(mysharedP.getKey(KEY_COMMENT), itemType)


        adapter = CommentsAdapter(comments,this)
        linearLayoutManager = LinearLayoutManager(this)
        commentRecyclerView.layoutManager= linearLayoutManager
        commentRecyclerView.adapter = adapter

    }

    private fun updateInfo(list:List<Comment>){
        adapter.updateList(list)
    }
    override fun onItemClickListener(item: Comment) {
        TODO("Not yet implemented")
    }
}