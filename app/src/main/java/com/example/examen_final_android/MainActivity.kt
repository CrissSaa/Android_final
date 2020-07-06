package com.example.examen_final_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen_final_android.network.Repository
import com.example.examen_final_android.network.UserResponse
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

        callService()

        txtIrFeed.setOnClickListener { view ->

            // Intent intent = new Intent(this, SecondActivity.class); <- Java
            val intent = Intent(this, PostActivity::class.java)
            // intent.putExtra("usuario", "Everis")
            startActivity(intent)
        }

        txtIrAmigos.setOnClickListener { view ->

            // Intent intent = new Intent(this, SecondActivity.class); <- Java
            val intent = Intent(this, UsersActivity::class.java)
            // intent.putExtra("usuario", "Everis")
            startActivity(intent)
        }

    }


    private fun callService() {
        val service = Repository.RetrofitRepository.getService()

        //GlobalScope.launch(Dispatchers.IO)
        //CoroutineScope(Dispatchers.IO).launch
        GlobalScope.launch(Dispatchers.IO) {
            val response =  service.getProfile()
            withContext(Dispatchers.Main) {
                try {
                    if(response.isSuccessful) {

                        val user : UserResponse?  = response.body()
                        if( user != null) SetearInfo(user)
                    }else{
                        Toast.makeText(this@MainActivity, "Error ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }catch (e : HttpException) {
                    Toast.makeText(this@MainActivity, "Error ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun SetearInfo(user: UserResponse) {
        if(user.image.isNotEmpty()){
            Picasso.get().load(user.image).into(profile_image)
        }

        profile_fullname.text = String.format("%s %s", user.name, user.lastname)
        profile_email.text = user.email
        profile_years.text = user.age
        profile_location.text = user.location
        profile_occupation.text = user.occupation
        profile_likes.text = user.social.likes.toString()
        profile_posts.text = user.social.posts.toString()
        profile_shares.text = user.social.shares.toString()
        profile_friends.text = user.social.shares.toString()
    }
}