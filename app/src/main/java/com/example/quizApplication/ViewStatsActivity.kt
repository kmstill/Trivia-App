package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewStatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stats)
        val userId = intent.getStringExtra(Constants.USER_ID)
        displayStats(userId)
        findViewById<Button>(R.id.btn_back).setOnClickListener {
            val mainMenuIntent = Intent(this, MainMenuActivity::class.java)
            mainMenuIntent.putExtra(Constants.USER_ID, userId)
            startActivity(mainMenuIntent)
        }
    }

    private fun displayStats(userId: String?) {
        val database = userId?.let { Firebase.database.reference.child("Users").child(it) }!!
        database.get().addOnSuccessListener {
            val currNumGamesPlayed = it.child("gamesPlayed").getValue(false).toString().toInt()
            val currNumQuestions = it.child("totalQuestions").getValue(false).toString().toInt()
            val currNumCorrect = it.child("correctAnswers").getValue(false).toString().toInt()
            val tvSinglePlayer = findViewById<TextView>(R.id.tv_single_player)
            tvSinglePlayer.text =
                "Total Games Played: $currNumGamesPlayed\nTotal Questions answered: $currNumQuestions\nTotal Correct Answers: $currNumCorrect"
        }
    }
}