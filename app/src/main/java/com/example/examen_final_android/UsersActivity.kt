package com.example.examen_final_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen_final_android.adapter.UsersAdapter
import com.example.examen_final_android.network.Repository
import com.example.examen_final_android.network.UsersResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UsersActivity : AppCompatActivity(), UsersAdapter.UsersHolder.OnAdapterListener  {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        adapter = UsersAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        usersRecyclerView.layoutManager= linearLayoutManager
        usersRecyclerView.adapter = adapter

        callService()

    }

    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getUsers()

            withContext(Dispatchers.Main) {

                try {
                    if(response.isSuccessful) {
                        val users : List<UsersResponse>?  = response.body()
                        if( users != null) updateInfo(users)
                    }else{
                        Toast.makeText(this@UsersActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@UsersActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateInfo(list: List<UsersResponse>) {
        adapter.updateList(list)
    }

    override fun onItemClickListener(item: UsersResponse) {
        Toast.makeText(this, "Click item ${item.username}", Toast.LENGTH_LONG).show()

        val userString : String = Gson().toJson(item, UsersResponse::class.java)
        Log.d("GSON Class to String", userString )
        /**
         * puedes enviar los extras a una pantalla de detalle
         */

        val user : UsersResponse = Gson().fromJson(userString, UsersResponse::class.java)
        Log.d("GSON string to class", user.username )
    }



}