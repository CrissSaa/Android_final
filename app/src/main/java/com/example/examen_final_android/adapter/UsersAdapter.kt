package com.example.examen_final_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_final_android.R
import com.example.examen_final_android.network.UsersResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_amigo.view.*
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter (private var data: List<UsersResponse>, private val listener: UsersAdapter.UsersHolder.OnAdapterListener) :
    RecyclerView.Adapter<UsersAdapter.UsersHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        val inflatedView = parent.inflate(R.layout.item_amigo, false)
        return UsersHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(usersList: List<UsersResponse>) {
        this.data = usersList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        val users: UsersResponse = this.data[position]
        holder.itemView.tv_feed_amigo.text = users.username

        if(!users.image.isBlank()) {
            Picasso.get()
                .load(users.image)
                .into(holder.itemView.iv_feed_amigo)
        }
        holder.itemView.setOnClickListener { listener.onItemClickListener(users) }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class UsersHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: UsersResponse)
        }

    }
}