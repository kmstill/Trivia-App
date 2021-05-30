package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        val confirmPassword = findViewById<EditText>(R.id.et_confirm_password)
        val register = findViewById<Button>(R.id.btn_register)

        register.setOnClickListener {
            val emailString = email.text.toString()
            val passwordString = password.text.toString()
            when {
                !isValidEmail(emailString) -> {
                    Toast.makeText(this, "Enter a valid email!", Toast.LENGTH_SHORT).show()
                    }
                passwordString.length < 8 ->{
                    Toast.makeText(this, "Password must be at least 8 characters!", Toast.LENGTH_SHORT).show()
                }
                passwordString !=confirmPassword.text.toString() ->{
                    Toast.makeText(this, "Passwords must match!", Toast.LENGTH_SHORT).show()
                }
                else ->{
                    createAccount(emailString, passwordString)
                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    /**
     * Email validation function taken from stackoverflow:
     * https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
     */
    private fun isValidEmail(email: String): Boolean{
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Creates user account using email and password.
     * Taken from firebase documentation:
     * https://firebase.google.com/docs/auth/android/password-auth
     */
    private fun createAccount(email: String, password: String){
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Registration Successful.",
                        Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SelectModeActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        // [END create_user_with_email]
    }

    /**
     * TAG definition from firebase documentation
     *https://github.com/firebase/snippets-android/blob/14cfefcaf00f9aae6f613281f46000d15f9047be/
     * auth/app/src/main/java/com/google/firebase/quickstart/auth/kotlin/EmailPasswordActivity.kt#L41-L55
     */
    companion object {
        private const val TAG = "EmailPassword"
    }

}