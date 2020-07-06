package com.example.tallerkotlin_final_everis

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_final_everis.network.FeedResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout_feed.view.*

class RecyclerAdapter(private var activity: AppCompatActivity, private var data: List<FeedResponse>, private val listener: FeedHolder.OnAdapterListener) :
    RecyclerView.Adapter<RecyclerAdapter.FeedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val inflatedView = parent.inflate(R.layout.item_layout_feed, false)
        return FeedHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
    fun updateList(feedList: List<FeedResponse>){
        this.data = feedList
        this.notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        val feed : FeedResponse = this.data[position]
        holder.itemView.tv_username_feed.text = feed.username
        holder.itemView.tv_body_feed.text = feed.body
        holder.itemView.tv_likes_feed.text = feed.likes.toString()


        if(!feed.user_image.isBlank()){
            Picasso.get()
                .load(feed.image)
                .into(holder.itemView.image_feed!!)
        }
        if(!feed.user_image.isBlank()){
            Picasso.get()
                .load(feed.user_image)
                .into(holder.itemView.image_user_feed_top)
        }

        holder.itemView.setOnClickListener{listener.onItemClickListener(feed)}
        holder.itemView.tv_comments_feed.setOnClickListener{
            val mSharedP = mSharedPreferences(activity)
            val comments = Gson().toJson(feed.comment)
            Log.e("myshareRecyclerAdapter",comments)

            mSharedP.put(DetailFeedActivity.KEY_COMMENT, comments)
            mSharedP.save()
            val intent = Intent(activity, DetailFeedActivity::class.java)
            activity.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class FeedHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }
        interface OnAdapterListener {
            fun onItemClickListener( item: FeedResponse)
        }

    }


}