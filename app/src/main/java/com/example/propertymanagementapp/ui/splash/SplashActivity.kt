package com.example.propertymanagementapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.ui.auth.SignInActivity

class SplashActivity : AppCompatActivity() {
    private val delayedTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }, delayedTime)
    }
}