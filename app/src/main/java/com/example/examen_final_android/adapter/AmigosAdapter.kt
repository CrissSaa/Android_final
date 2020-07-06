package com.example.examen_final_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.examen_final_android.R
import com.example.examen_final_android.network.AmigosResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_amigo.view.*

class AmigosAdapter (private var data: List<AmigosResponse>, private val listener: AmigosAdapter.AmigosHolder.OnAdapterListener) :
    RecyclerView.Adapter<AmigosAdapter.AmigosHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmigosHolder {
        val inflatedView = parent.inflate(R.layout.item_amigo, false)
        return AmigosHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(AmigosList: List<AmigosResponse>) {
        this.data = AmigosList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AmigosHolder, position: Int) {
        val amigos: AmigosResponse = this.data[position]
        holder.itemView.tv_comment.text = amigos.comment

        if(!amigos.user_image.isBlank()) {
            Picasso.get()
                .load(amigos.user_image)
                .into(holder.itemView.iv_comment)
        }
        holder.itemView.setOnClickListener { listener.onItemClickListener(amigos) }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class AmigosHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: AmigosResponse)
        }

    }

}