package com.example.quizApplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        val btnStart=findViewById<Button>(R.id.btn_start)
        val etName=findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.et_name)
        btnStart.setOnClickListener {
            when {
                etName.text.toString().isEmpty() -> {
                    Toast.makeText(this,
                        "Please enter your name", Toast.LENGTH_SHORT).show()
                }
                etName.text.toString().length==1 -> {
                    Toast.makeText(this, "Name must be at least two characters", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val intent= Intent(this,QuizQuestionsActivity::class.java)
                    intent.putExtra(Constants.USER_NAME, findViewById<androidx.appcompat.widget.AppCompatEditText>(R.id.et_name).text.toString())
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}