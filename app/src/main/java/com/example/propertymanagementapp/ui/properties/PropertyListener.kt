package com.example.propertymanagementapp.ui.properties

import androidx.lifecycle.LiveData
import com.example.propertymanagementapp.data.model.PropertyResponse

interface PropertyListener {
    fun onStarted()
    fun onSuccess(response: LiveData<PropertyResponse>)
    fun failure(message: String)
}