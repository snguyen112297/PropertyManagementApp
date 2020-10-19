package com.example.propertymanagementapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.Task
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.ui.todo.TaskDetailsActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_adapter_task.view.*

class AdapterTask(var mContext: Context): RecyclerView.Adapter<AdapterTask.MyViewHolder>(){
    private var mList: ArrayList<Task> = ArrayList()
    private var keyList: ArrayList<String> = ArrayList()
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTask.MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_adapter_task, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterTask.MyViewHolder, position: Int) {
        val task = mList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(list: ArrayList<Task>, kList: ArrayList<String>){
        mList = list
        keyList = kList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(task: Task){
            val sessionManager = SessionManager(mContext)
            val databaseReference = FirebaseDatabase.getInstance().getReference(Task.TASK_NAME)
                .child(sessionManager.getUserId())
                .child(keyList[layoutPosition])
            itemView.task_adapter_title.text = task.title.toString()
            itemView.task_adapter_title.setOnClickListener{
                var intent = Intent(mContext, TaskDetailsActivity::class.java)
                intent.putExtra("title", task.title.toString())
                intent.putExtra("description", task.description.toString())
                mContext.startActivity(intent)
            }
            itemView.task_adapter_delete.setOnClickListener{
                var builder = AlertDialog.Builder(mContext)
                builder.setTitle("Task Termination")
                builder.setMessage("Terminate this task?")
                builder.setNegativeButton("No"){
                        dialog, _ ->  dialog?.dismiss()
                }
                builder.setPositiveButton("Yes"){
                        _, _ -> run {
                        databaseReference.setValue(null)
                        mList.removeAt(layoutPosition)
                        notifyItemChanged(layoutPosition)
                    }
                }
                builder.create().show()
            }
            if (itemView.task_adapter_toggle.isChecked != task.status){
                itemView.task_adapter_toggle.toggle()
            }
            itemView.task_adapter_toggle.setOnClickListener{
                databaseReference.child("status").setValue(itemView.task_adapter_toggle.isChecked)
            }
        }
    }

}