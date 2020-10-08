package com.example.propertymanagementapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.propertymanagementapp.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        init()
    }

    private fun init(){
        sign_in_login.setOnClickListener(this)
        sign_in_register.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            sign_in_login -> startActivity(Intent(this, LoginActivity::class.java))
            sign_in_register -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}