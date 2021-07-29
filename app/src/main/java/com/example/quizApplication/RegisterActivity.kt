package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
                passwordString.length < 8 -> {
                    Toast.makeText(
                        this,
                        "Password must be at least 8 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                passwordString != confirmPassword.text.toString() -> {
                    Toast.makeText(this, "Passwords must match!", Toast.LENGTH_SHORT).show()
                }
                else -> {
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
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Creates user account using email and password.
     * Taken from firebase documentation:
     * https://firebase.google.com/docs/auth/android/password-auth
     */
    private fun createAccount(email: String, password: String) {
        val auth: FirebaseAuth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserStatsToDatabase(auth, email)
                    Toast.makeText(
                        baseContext, "Registration Successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    var mainMenuIntent = Intent(this, MainMenuActivity::class.java)
                    mainMenuIntent.putExtra(Constants.USER_ID, auth.uid)
                    startActivity(mainMenuIntent)
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun addUserStatsToDatabase(auth: FirebaseAuth, email: String) {
        val userId = auth.currentUser?.uid
        val database = Firebase.database.reference
        if (userId != null) {
            val user = database.child("Users").child(userId)
            val userStats = UserStats()
            user.child("stats").setValue(userStats)
            user.child("email").setValue(email)
        }
    }

}