
package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //val username=intent.getStringExtra(Constants.USER_NAME)
        //findViewById<TextView>(R.id.tv_name).text=username
        //val total_questions=intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        //val correct_answers=intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        //findViewById<TextView>(R.id.tv_score).text="Your score is $correct_answers out of $total_questions."

        //findViewById<Button>(R.id.btn_finish).setOnClickListener {
           // startActivity(Intent(this, StartActivity::class.java ))
        //}
    }
}