package com.example.propertymanagementapp.data.repo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.RegisterResponse
import com.example.propertymanagementapp.data.model.User
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.example.propertymanagementapp.ui.home.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository{
    fun login(context: Context, email: String, password: String, mode: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()
        MyApi().login(User(email, "", password, ""))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>, response: Response<LoginResponse>
                ) {
                    if (response.body() != null) {
                        if (response.body()!!.user != null) {
                            if (response.body()!!.user.type.equals(mode)) {
                                loginResponse.value = "Logged In"
                            } else context.toast("Are you sure you are a $mode?")
                        }
                    } else {
                        context.toast("Wrong email or password")
                    }
                    context.d(response.body().toString())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    context.d(t.message.toString())
                }
        })
        return loginResponse
    }

    fun register(context: Context, email: String,
                 password: String, confirmPassword: String,
                 name: String, mode: String): LiveData<String>{
        val registerResponse = MutableLiveData<String>()
        if (password.length <= 6){
            context.toast("Password must be longer than 6 characters")
        } else {

            // Check if confirm password matches
            if (password == confirmPassword){
                MyApi().register(User(email, name, password, mode))
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>
                        ) {
                            if (response.body() != null){
                                if (response.body()!!.data != null) {
                                    registerResponse.value = "Registered"
                                }
                            }
                            context.d(response.body().toString())
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            context.d(t.message.toString())
                        }
                })
            } else {
                context.toast("Confirm password does not match")
            }
        }
        return registerResponse
    }
}