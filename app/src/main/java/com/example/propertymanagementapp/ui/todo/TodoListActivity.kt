package com.example.propertymanagementapp.ui.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.adapters.AdapterTask
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var databaseReference: DatabaseReference

    private lateinit var mList: ArrayList<Task>

    private lateinit var keyList: ArrayList<String>

    private lateinit var adapterTask: AdapterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        var sessionManager = SessionManager(this)
        databaseReference = FirebaseDatabase.getInstance().getReference(Task.TASK_NAME).child(sessionManager.getUserId())

        init()
    }

    private fun init(){
        var toolbar = toolbar
        toolbar.title = "To-do"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        adapterTask = AdapterTask(this)
        todo_recycler_view.adapter = adapterTask
        todo_recycler_view.layoutManager = LinearLayoutManager(this)

        todo_add_button.setOnClickListener(this)

        getData()

    }

    override fun onClick(view: View?) {
        when (view) {
            todo_add_button -> startActivity(Intent(this, AddTaskActivity::class.java))
        }
    }

    private fun getData(){
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                applicationContext.toast("Big oof getting to-do list tasks")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mList = ArrayList()
                keyList = ArrayList()
                for (data in snapshot.children){
                    var task = data.getValue(Task::class.java)
                    var key = data.key
                    mList.add(task!!)
                    keyList.add(key!!)
                }
                adapterTask.setData(mList, keyList)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onResume() {
        getData()
        super.onResume()
    }
}