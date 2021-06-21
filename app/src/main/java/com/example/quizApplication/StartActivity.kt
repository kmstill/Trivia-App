package com.example.quizApplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class StartActivity : AppCompatActivity() {

    private var login: Button? = null
    private var register: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        login=findViewById<Button>(R.id.btn_login)
        register=findViewById<Button>(R.id.btn_register)


        login!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
        register!!.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        })

    }
}