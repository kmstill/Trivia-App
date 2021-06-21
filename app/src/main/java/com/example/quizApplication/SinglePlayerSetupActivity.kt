package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.FirebaseAuth

class SinglePlayerSetupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player_setup)

        val etPlayer1 = findViewById<TextView>(R.id.et_player_1)
        val etNumQuestions = findViewById<TextView>(R.id.et_num_questions)


        findViewById<Button>(R.id.btn_play_game).setOnClickListener {
            when {
                etPlayer1.text.isEmpty() or etNumQuestions.text.isEmpty() -> {
                    Toast.makeText(
                        baseContext, "All fields must be filled out",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !etNumQuestions.text.isDigitsOnly() or (etNumQuestions.text.toString().toInt() < 1)
                        or (etNumQuestions.text.toString().toInt() > 20) -> {
                    Toast.makeText(
                        baseContext, "Number of questions must be a number between 0 and 20",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val singlePlayerIntent = Intent(this, SinglePlayerActivity::class.java)
                    singlePlayerIntent.putExtra(Constants.USER_NAME, etPlayer1.text.toString())
                    singlePlayerIntent.putExtra(
                        Constants.NUM_QUESTIONS,
                        etNumQuestions.text.toString().toInt()
                    )
                    singlePlayerIntent.putExtra(
                        Constants.USER_ID,
                        intent.getStringExtra(Constants.USER_ID)
                    )
                    startActivity(singlePlayerIntent)
                }
            }
        }
        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                baseContext, "Logged Out",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this, StartActivity::class.java))
        }
    }
}