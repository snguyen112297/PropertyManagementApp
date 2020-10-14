package com.example.propertymanagementapp.ui.auth

import androidx.lifecycle.LiveData
import com.example.propertymanagementapp.data.model.RegisterResponse

interface RegisterListener {
    fun onStarted()
    fun onSuccess(response: LiveData<RegisterResponse>)
    fun failure(message: String)
}