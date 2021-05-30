//package com.example.quizApplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quizApplication.GamePlayFunctions
import com.example.quizApplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TestActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private val TAG = "ReadAndWriteSnippets"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        //initializeDbRef()
        pray(database)
        GamePlayFunctions.getQValEv(10)

    }



    private fun pray(postReference: DatabaseReference) {
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.w(TAG, "in ondatachange")
                val question = dataSnapshot.child("10").getValue(false).toString()
                Log.w(TAG, "$question")
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "in onCancelled")
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }


    private fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
        // [END initialize_database_ref]
    }

}