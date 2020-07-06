package com.example.tallerkotlin_final_everis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.tallerkotlin_final_everis.network.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_feed_comments.view.*

class CommentsAdapter(private var data: List<Comment>, private val listener: CommentsAdapter.CommentsHolder.OnAdapterListener) :
    RecyclerView.Adapter<CommentsAdapter.CommentsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.CommentsHolder {
        val inflatedView = parent.inflate(R.layout.item_feed_comments, false)
        return CommentsHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(commentList: List<Comment>) {
        this.data = commentList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CommentsAdapter.CommentsHolder, position: Int) {
        val comment: Comment = this.data[position]
        holder.itemView.tv_feed_comments_text.text = comment.comment

        if (!comment.user_image.isBlank()) {
            Picasso.get()
                .load(comment.user_image)
                .into(holder.itemView.image_user_feed_comments!!)
        }


        holder.itemView.setOnClickListener { listener.onItemClickListener(comment) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CommentsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {


        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener(item: Comment)
        }

    }
}