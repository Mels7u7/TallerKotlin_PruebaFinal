package com.example.tallerkotlin_final_everis

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallerkotlin_final_everis.network.FeedResponse
import com.example.tallerkotlin_final_everis.network.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FeedActivity : AppCompatActivity(), RecyclerAdapter.FeedHolder.OnAdapterListener {


    private lateinit var adapter: RecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        adapter = RecyclerAdapter(this,ArrayList(),this)
        linearLayoutManager = LinearLayoutManager(this)
        feedRecyclerView.layoutManager= linearLayoutManager
        feedRecyclerView.adapter = adapter
        callService()



    }
    private fun callService() {

        val service = Repository.RetrofitRepository.getService()
        //GlobalScope.launch
        GlobalScope.launch (Dispatchers.IO){
            val response =  service.getFeed()

            withContext(Dispatchers.Main) {

                try {

                    if(response.isSuccessful) {

                        val feed : List<FeedResponse>?  = response.body()
                        if( feed != null) updateInfo(feed)
                    }else{
                        Toast.makeText(this@FeedActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FeedActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun updateInfo(list:List<FeedResponse>){
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: FeedResponse) {
        Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()

        val feedString : String = Gson().toJson(item, FeedResponse::class.java)
        Log.d("GSON Class to String", feedString )
        /**
         * puedes enviar los extras a una pantalla de detalle
         */

        val feed : FeedResponse = Gson().fromJson(feedString, FeedResponse::class.java)
        Log.d("GSON string to class", feed.username )
    }
}