package com.example.signup_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Retrieve user data sent from ProfileActivity
        val userName = intent.getStringExtra("user_name")
        val userEmail = intent.getStringExtra("user_email")
        val userPhone = intent.getStringExtra("user_phone")

        // Initialize TextViews to display user data
        val userNameTextView = findViewById<TextView>(R.id.userNameTextView)
        val userEmailTextView = findViewById<TextView>(R.id.userEmailTextView)
        val userPhoneTextView = findViewById<TextView>(R.id.userPhoneTextView)

        // Display user data in TextViews
        userNameTextView.text = "Name: $userName"
        userEmailTextView.text = "Email: $userEmail"
        userPhoneTextView.text = "Phone: $userPhone"
    }
}