package com.example.propertymanagementapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.ui.auth.SignInActivity
import com.example.propertymanagementapp.ui.properties.PropertyActivity
import com.example.propertymanagementapp.ui.todo.TodoListActivity
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
        sessionManager = SessionManager(this)

        main_properties_select.setOnClickListener(this)
        main_log_out.setOnClickListener(this)
        main_to_do.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view) {
            main_properties_select -> {
                startActivity(Intent(this, PropertyActivity::class.java))
            }
            main_log_out -> {
                sessionManager.logout()
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            main_to_do -> {
                startActivity(Intent(this, TodoListActivity::class.java))
            }
        }
    }
}