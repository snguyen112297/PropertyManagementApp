package com.example.propertymanagementapp.helpers

import android.content.Context
import com.example.propertymanagementapp.app.Config.Companion.ACCESS_TOKEN
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.RegisterResponse

class SessionManager(private var context: Context){
    private val FILE_NAME = "REGISTERED_USER"
    private val KEY_TOKEN = "TOKEN"
    private val USER_ID = "USER_ID"
    private val USER_TYPE = "USER_TYPE"
    var sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserLogin(loginResponse: LoginResponse){
        editor.putString(KEY_TOKEN, loginResponse.token)
        editor.putString(USER_ID, loginResponse.user._id)
        editor.putString(USER_TYPE, loginResponse.user.type)
        editor.commit()
    }

    fun saveUserRegister(registerResponse: RegisterResponse){
        editor.putString(USER_ID, registerResponse.data._id)
        editor.commit()
    }

    fun getQuickLogin(): Boolean {
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token != null
    }

    fun getUserId(): String{
        return sharedPreferences.getString(USER_ID, null)!!
    }


    fun getUserType(): String{
        return sharedPreferences.getString(USER_TYPE, null)!!
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }

    companion object{
        const val TOKEN = ACCESS_TOKEN
    }
}