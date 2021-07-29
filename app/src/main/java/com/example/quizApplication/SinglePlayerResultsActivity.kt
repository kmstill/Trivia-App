package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class SinglePlayerResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player_results)
        //Initialize Fields
        Log.d("kevDebug", "in results")
        val userId = intent.getStringExtra(Constants.USER_ID)
        displayResults()
        getCurrentStats(userId)

        findViewById<Button>(R.id.btn_main_menu).setOnClickListener {
            val mainMenuIntent = Intent(this, MainMenuActivity::class.java)
            mainMenuIntent.putExtra(Constants.USER_ID, userId)
            startActivity(mainMenuIntent)
        }
    }

    private fun displayResults() {
        Log.d("kevDebug", "in displayResults()")
        val username = intent.getStringExtra(Constants.USER_NAME)
        val totalQuestions = intent.getIntExtra(Constants.NUM_QUESTIONS, 0)
        val correctAnswers = intent.getIntExtra(Constants.NUM_CORRECT, 0)
        findViewById<TextView>(R.id.tv_congratulations).text = "Congratulations $username!"
        findViewById<TextView>(R.id.tv_score).text =
            "Your score is $correctAnswers out of $totalQuestions."
    }

    private fun getCurrentStats(userId: String?) {
        Log.d("kevDebug", "in getCurrentStats()")
        val database = userId?.let { Firebase.database.reference.child("Users").child(it).child("stats") }!!
        database.get().addOnSuccessListener {
            val gameNumQuestions = intent.getIntExtra(Constants.NUM_QUESTIONS, 0)
            val gameNumCorrect = intent.getIntExtra(Constants.NUM_CORRECT, 0)
            val currNumGamesPlayed = it.child("gamesPlayed").getValue(false).toString().toInt()
            val currNumQuestions = it.child("totalQuestions").getValue(false).toString().toInt()
            val currNumCorrect = it.child("correctAnswers").getValue(false).toString().toInt()
            val userStats =
                UserStats(
                    currNumGamesPlayed + 1,
                    currNumQuestions + gameNumQuestions,
                    currNumCorrect + gameNumCorrect
                )
            database.setValue(userStats)
            Log.d("kevDebug", "statsucc")
        }.addOnFailureListener {
            Log.d("kevDebug", "statfail")
        }
    }
}