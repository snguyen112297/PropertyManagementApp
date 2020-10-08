package com.example.propertymanagementapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.propertymanagementapp.data.repo.UserRepository

class AuthViewModel: ViewModel() {
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var name: String? = null
    var mode: String? = null

    var authListener: AuthListener? = null
    fun setAuthMode(m: String){
        mode = m
    }
    fun onLoginButtonClick(view: View){
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            // failure
            authListener?.failure("Big oof")
            return
        }
        // success
        val loginResponse = UserRepository().login(view.context, email!!, password!!, mode!!)
        authListener?.onSuccess(loginResponse)
    }
    fun onRegisterButtonClick(view: View){
        if (email.isNullOrEmpty() || password.isNullOrEmpty() || name.isNullOrEmpty()){
            // failure
            authListener?.failure("Big oof")
            return
        }
        // success
        val registerResponse = UserRepository().register(view.context, email!!, password!!, confirmPassword!!, name!!, mode!!)
        authListener?.onSuccess(registerResponse)
    }
}