package com.example.propertymanagementapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.AdapterTab
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init(){
        var tabAdapter = AdapterTab(supportFragmentManager, 2)
        view_pager.adapter = tabAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}