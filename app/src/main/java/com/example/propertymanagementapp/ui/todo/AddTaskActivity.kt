package com.example.propertymanagementapp.ui.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        firebaseDatabase = FirebaseDatabase.getInstance()
        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "Add Task"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        add_task_submit.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onClick(view: View?) {
        when (view){
            add_task_submit -> {
                val title = add_task_title.editText!!.text.toString()
                val description = add_task_description.editText!!.text.toString()
                if (title.isEmpty() || description.isEmpty()){
                    this.toast("Big oof. Fill in the blanks")
                } else {
                    val sessionManager = SessionManager(this)
                    val task = Task(title, description)
                    val databaseReference = firebaseDatabase.getReference(Task.TASK_NAME).child(sessionManager.getUserId())
                    val taskId = databaseReference.push().key
                    databaseReference.child(taskId!!).setValue(task)
                    this.toast("Task submitted")
                    finish()
                }
            }
        }
    }

}