package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainMenuActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        findViewById<Button>(R.id.btn_multiplayer_online).setOnClickListener(this)
        findViewById<Button>(R.id.btn_single_player).setOnClickListener(this)
        findViewById<Button>(R.id.btn_view_stats).setOnClickListener(this)
        findViewById<Button>(R.id.btn_logout).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var userId = intent.getStringExtra(Constants.USER_ID)
        when(v?.id){
            R.id.btn_multiplayer_online ->{
                //val multiPlayerIntent = Intent(this,MultiPlayerSetupActivity::class.java)
               // multiPlayerIntent.putExtra(Constants.USER_ID, userId)
                //startActivity(multiPlayerIntent)
            }
            R.id.btn_single_player ->{
                val singlePlayerIntent = Intent(this,SinglePlayerSetupActivity::class.java)
                singlePlayerIntent.putExtra(Constants.USER_ID,userId)
                startActivity(singlePlayerIntent)
            }
            R.id.btn_view_stats ->{
                val viewStatsIntent = Intent(this, ViewStatsActivity::class.java)
                viewStatsIntent.putExtra(Constants.USER_ID, userId)
                startActivity(viewStatsIntent)
            }
            R.id.btn_logout->{
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(baseContext, "Logged Out",
                    Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, StartActivity::class.java))
            }

        }
    }
}