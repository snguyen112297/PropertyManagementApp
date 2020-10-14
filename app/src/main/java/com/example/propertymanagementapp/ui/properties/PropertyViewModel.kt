package com.example.propertymanagementapp.ui.properties

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.propertymanagementapp.data.repo.PropertyRepo

class PropertyViewModel : ViewModel(){
    var propertyListener: PropertyListener? = null
    fun getProperties(context: Context){
        val propertyResponse = PropertyRepo().getProperties(context)
        propertyListener?.onSuccess(propertyResponse)
    }

    fun onAddPropertyClick(view: View){
        view.context.startActivity(Intent(view.context, AddPropertyActivity::class.java))
    }
}