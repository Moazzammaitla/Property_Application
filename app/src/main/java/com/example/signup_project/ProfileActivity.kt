package com.example.signup_project


import UserAdapter
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.signup_project.databinding.ActivityProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var database: MyAppDatabase
    private lateinit var currentUserId: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Retrieve the currentUserId from the intent
        currentUserId = intent.getStringExtra("currentUserId") ?: " "

        // Initialize the Room database instance
        database = Room.databaseBuilder(
            applicationContext,
            MyAppDatabase::class.java,
            "myapp_database"
        ).build()

        // Initialize RecyclerView and its adapter
        userAdapter = UserAdapter()
        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = userAdapter


        // Handle "Go to User Profile" button click
        binding.viewProfileButton.setOnClickListener {
            // Query the database for the current user's data using the current user ID
            GlobalScope.launch(Dispatchers.IO) {
                val user = database.userDao().getUserById(currentUserId)

                // Create an intent to open the UserProfileActivity
                val intent = Intent(this@ProfileActivity, UserProfileActivity::class.java)

                // Pass the user data as extras to the intent
                intent.putExtra("user_name", user?.name)
                intent.putExtra("user_email", user?.email)
                intent.putExtra("user_phone", user?.phone)

                // Start the UserProfileActivity
                runOnUiThread {
                    startActivity(intent)
                }
            }
        }

        // Handle "Show Data" button click
        binding.showDataButton.setOnClickListener {
            // Fetch all user data from the Room database and update the RecyclerView
            GlobalScope.launch(Dispatchers.IO) {
                val users = database.userDao().getAllUsers()
                runOnUiThread {
                    userAdapter.submitList(users)
                }
            }
        }
        // Handle "Go to Insert property Activity" button click
        binding.addpropertyButton.setOnClickListener {
            val intent = Intent(this,InsertPropertyActivity::class.java)
            startActivity(intent)
        }

        // Handle "Go to veiw property Activity" button click
        binding.veiwpropertyButton.setOnClickListener {
            val intent = Intent(this,ViewPropertyActivity::class.java)
            startActivity(intent)
        }



    }

}