package com.example.propertymanagementapp.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.propertymanagementapp.R
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Task Details"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        var title = "Title: " + intent.getStringExtra("title")
        var description = "Description: " + intent.getStringExtra("description")
        task_details_title.text = title
        task_details_description.text = description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}