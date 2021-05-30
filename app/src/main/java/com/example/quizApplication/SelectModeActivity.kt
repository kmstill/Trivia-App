package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SelectModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_mode)
        findViewById<Button>(R.id.btn_multiplayer_online).setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
            startActivity(Intent(this, StartActivity::class.java))
        }
        findViewById<Button>(R.id.btn_single_player).setOnClickListener {
            startActivity(Intent(this, SinglePlayerSetupActivity::class.java))
        }
        findViewById<Button>(R.id.btn_view_stats).setOnClickListener {
            startActivity(Intent(this, ViewStatsActivity::class.java))
        }
        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Logged Out",
                Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, StartActivity::class.java))
        }
    }
}