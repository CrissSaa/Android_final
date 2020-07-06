package com.example.examen_final_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen_final_android.adapter.PostAdapter
import com.example.examen_final_android.network.PostResponse
import com.example.examen_final_android.network.Repository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.item_post.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PostActivity : AppCompatActivity(), PostAdapter.PostHolder.OnAdapterListener {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        adapter = PostAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        postRecyclerView.layoutManager= linearLayoutManager
        postRecyclerView.adapter = adapter

        callService()

        /*btn_feed_item_comment.setOnClickListener { view ->

            // Intent intent = new Intent(this, SecondActivity.class); <- Java
            val intent = Intent(this, UsersActivity::class.java)
            // intent.putExtra("usuario", "Everis")
            startActivity(intent)
        }*/
    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getPosts()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val posts : List<PostResponse>?  = response.body()
                        if( posts != null) updateInfo(posts)
                    }else{
                        Toast.makeText(this@PostActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@PostActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<PostResponse>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: PostResponse) {
        Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()

        val postString : String = Gson().toJson(item, PostResponse::class.java)
        Log.d("GSON Class to String", postString )


        val post : PostResponse = Gson().fromJson(postString, PostResponse::class.java)
        Log.d("GSON string to class", post.username )


        //val intent = Intent(this, AmigosActivity::class.java)
        //intent.putExtra("objetoComen", postString)
        //startActivity(intent)
    }

}