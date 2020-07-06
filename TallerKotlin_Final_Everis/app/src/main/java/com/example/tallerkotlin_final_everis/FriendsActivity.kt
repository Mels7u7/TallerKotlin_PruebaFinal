package com.example.tallerkotlin_final_everis

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tallerkotlin_final_everis.network.FriendResponse
import com.example.tallerkotlin_final_everis.network.Repository
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FriendsActivity : AppCompatActivity(), FriendsAdapter.FriendHolder.OnAdapterListener{
    private lateinit var adapter: FriendsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        adapter = FriendsAdapter(this,ArrayList(),this)
        linearLayoutManager = LinearLayoutManager(this)
        friendRecyclerView.layoutManager= linearLayoutManager
        friendRecyclerView.adapter = adapter
        callService()
    }
    private fun callService() {

        val service = Repository.RetrofitRepository.getService()
        //GlobalScope.launch
        GlobalScope.launch (Dispatchers.IO){
            val response =  service.getFriends()

            withContext(Dispatchers.Main) {

                try {

                    if(response.isSuccessful) {

                        val friend : List<FriendResponse>?  = response.body()
                        if( friend != null) updateInfo(friend)
                    }else{
                        Toast.makeText(this@FriendsActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@FriendsActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun updateInfo(list:List<FriendResponse>){
        adapter.updateList(list)
    }
    override fun onItemClickListener(item: FriendResponse) {
        TODO("Not yet implemented")
    }
}