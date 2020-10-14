package com.example.propertymanagementapp.ui.auth

import androidx.lifecycle.LiveData
import com.example.propertymanagementapp.data.model.LoginResponse

interface LoginListener {
    fun onStarted()
    fun onSuccess(response: LiveData<LoginResponse>)
    fun failure(message: String)
}