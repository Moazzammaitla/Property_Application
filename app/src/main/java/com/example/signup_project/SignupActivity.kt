package com.example.signup_project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.signup_project.databinding.ActivityProfileBinding
import com.example.signup_project.databinding.ActivitySignupBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    // SharedPreferences file name
    private val PREFS_NAME = "MyPrefsFile"

    private lateinit var binding:ActivitySignupBinding
    lateinit var db:MyAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Initialize SharedPreferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Handle "Go to Login Activity" button click
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // Handle signup button click
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // Validate the input fields
            if (validateInput(name, phone, email, password)) {

                // Save user data to Room database
                val user = User(name =  name, phone = phone, email =  email, password =  password)
                saveUserDataToDatabase(user)

                // Save user data to SharedPreferences
                with(sharedPreferences.edit()) {
                    putString("name", name)
                    putString("phone", phone)
                    putString("email", email)
                    putString("password", password)
                    putBoolean("key",true)
                    apply()

                }

                // Navigate to the LoginActivity
                val loginIntent = Intent(this, LoginActivity::class.java)

                startActivity(loginIntent)

            }
            else {
                // Display an error message or handle invalid input
                Toast.makeText(this, "Invalid input. Please check your data.", Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun validateInput(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Boolean {
        return ValidationUtil.isValidName(name) &&
                ValidationUtil.isValidPhoneNumber(phone) &&
                ValidationUtil.isValidEmail(email) &&
                ValidationUtil.isValidPassword(password)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveUserDataToDatabase(user: User) {
        db = MyAppDatabase.getInstance(this)
        GlobalScope.launch {
            // Insert the user data into the Room database
            db.userDao().insertUser(user)
        }
    }

}

