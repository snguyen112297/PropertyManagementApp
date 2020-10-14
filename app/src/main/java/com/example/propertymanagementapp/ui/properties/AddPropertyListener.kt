package com.example.propertymanagementapp.ui.properties

import androidx.lifecycle.LiveData
import com.example.propertymanagementapp.data.model.AddPropertyResponse

interface AddPropertyListener {
    fun onStarted()
    fun onSuccess(response: LiveData<AddPropertyResponse>)
    fun failure(message: String)
}