package com.example.signup_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class SplashActivity : AppCompatActivity() {

    // Duration of the splash screen in milliseconds (e.g., 2000ms or 2 seconds)
    private val splashDuration: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use CountDownTimer to delay the transition to the main activity
        object : CountDownTimer(splashDuration, splashDuration) {
            override fun onTick(millisUntilFinished: Long) {
                // Not used
            }

            override fun onFinish() {
                val mainIntent = Intent(this@SplashActivity, SignupActivity::class.java)
                startActivity(mainIntent)
                finish() // Close the splash activity so it's not accessible via back button
            }
        }.start()
    }
}