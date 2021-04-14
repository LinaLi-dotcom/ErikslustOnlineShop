package com.example.erikslustonlineshop.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.erikslustonlineshop.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

        findViewById<TextView>(R.id.tv_user_id).text = "User ID :: $userId"
        findViewById<TextView>(R.id.tv_email_id).text = "User ID :: $emailId"

        findViewById<Button>(R.id.btn_logout).setOnClickListener {

            //logout from appen
            FirebaseAuth.getInstance().signOut()


        }
    }
}