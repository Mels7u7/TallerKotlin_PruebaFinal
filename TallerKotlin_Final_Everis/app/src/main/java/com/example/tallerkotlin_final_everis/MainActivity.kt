package com.example.tallerkotlin_final_everis

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.tallerkotlin_final_everis.network.Repository
import com.example.tallerkotlin_final_everis.network.UserResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        icon_loading.visibility = View.VISIBLE
        callService()
        tv_gofeed.setOnClickListener {
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
        }
        tv_gofriends.setOnClickListener {
            val intent = Intent(this@MainActivity, FriendsActivity::class.java)
            startActivity(intent)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun callService() {

        val service = Repository.RetrofitRepository.getService()
        //GlobalScope.launch
        GlobalScope.launch (Dispatchers.IO){
            val response =  service.getProfile()

            withContext(Dispatchers.Main) {

                try {
                    icon_loading.visibility = View.GONE
                    if(response.isSuccessful) {

                        val user : UserResponse?  = response.body()
                        if( user != null) {

                            Picasso.get()
                                .load(user.image)
                                .into(image_user)

                            tv_likes.setText(user.social.likes.toString())
                            tv_friend.setText(user.social.friends.toString())
                            tv_post.setText(user.social.posts.toString())
                            tv_share.setText(user.social.shares.toString())
                            tv_user.setText(user.name +" "+user.lastname).toString()
                            tv_years.setText(user.age).toString()
                            tv_correo.setText(user.email).toString()
                            tv_location.setText(user.location).toString()
                            tv_job.setText(user.occupation).toString()
                        }
                    }else{
                        Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
