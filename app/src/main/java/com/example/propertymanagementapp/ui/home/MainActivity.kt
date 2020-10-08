package com.example.propertymanagementapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.propertymanagementapp.R
import kotlinx.android.synthetic.main.action_bar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }
}