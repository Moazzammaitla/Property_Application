package com.example.signup_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.signup_project.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {


    lateinit var db:MyAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle "Go to Login Activity" button click
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)

            startActivity(intent)
        }



        // Handle login button click
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()


            db = MyAppDatabase.getInstance(this)
            GlobalScope.launch {
                val user= db.userDao().getUserByEmail(email)

                if (user != null && user.password == password) {
                    // If login is successful, navigate to the user profile or dashboard activity
                    val intent = Intent(this@LoginActivity,ProfileActivity::class.java)
                    intent.putExtra("currentUserId", user.id.toString())
                    startActivity(intent)
                    finish()
                } else {
                    // Display an error message or handle invalid login
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Invalid login", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



}