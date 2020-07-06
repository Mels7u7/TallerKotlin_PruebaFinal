package com.example.tallerkotlin_final_everis

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_final_everis.network.FeedResponse
import com.example.tallerkotlin_final_everis.network.FriendResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_activity_friend.view.*
import kotlinx.android.synthetic.main.item_layout_feed.view.*

class FriendsAdapter(private var activity: AppCompatActivity, private var data: List<FriendResponse>, private val listener: FriendsAdapter.FriendHolder.OnAdapterListener) :
RecyclerView.Adapter<FriendsAdapter.FriendHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.FriendHolder {
        val inflatedView = parent.inflate(R.layout.item_activity_friend, false)
        return FriendsAdapter.FriendHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    fun updateList(friendList: List<FriendResponse>){
        this.data = friendList
        this.notifyDataSetChanged()
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        val friend : FriendResponse = this.data[position]
        holder.itemView.tv_friend_name.text = friend.name +" "+friend.lastname

        if(!friend.image.isBlank()){
            Picasso.get()
                .load(friend.image)
                .into(holder.itemView.image_user_friend!!)
        }

        holder.itemView.setOnClickListener{listener.onItemClickListener(friend)}
        holder.itemView.card_friends.setOnClickListener{
            //val mSharedP = mSharedPreferences(activity)
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" +friend.phone))
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE),
                    1)

            }else {
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class FriendHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{


        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View? ) {

        }
        interface OnAdapterListener {
            fun onItemClickListener( item: FriendResponse)

        }

    }
}