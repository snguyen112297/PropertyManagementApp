package com.example.propertymanagementapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.AdapterTab
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init(){
        var tabAdapter = AdapterTab(supportFragmentManager, 1)
        view_pager.adapter = tabAdapter
        tab_layout.setupWithViewPager(view_pager)
    }
}