package com.example.propertymanagementapp.data.repo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.RegisterResponse
import com.example.propertymanagementapp.data.model.RegisterUser
import com.example.propertymanagementapp.data.model.User
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserRepository{
    @SuppressLint("CheckResult")
    fun login(context: Context, email: String, password: String, mode: String): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        MyApi().login(User("", email, "", password, ""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<LoginResponse>(){
                override fun onSuccess(response: LoginResponse) {
                    if (response != null) {
                        if (response.user != null) {
                            if (response.user.type.equals(mode)) {
                                loginResponse.value = response
                            } else context.toast("Are you sure you are a $mode?")
                        }
                    } else {
                        context.toast("Wrong email or password")
                    }
                    context.d(response.toString())
                }
                override fun onError(t: Throwable) {
                    context.d(t.message.toString())
                }
            })
        return loginResponse
    }

    @SuppressLint("CheckResult")
    fun register(context: Context, email: String,
                 password: String, confirmPassword: String,
                 name: String, mode: String): LiveData<RegisterResponse>{
        val registerResponse = MutableLiveData<RegisterResponse>()
        if (password.length <= 6){
            context.toast("Password must be longer than 6 characters")
        } else {

            // Check if confirm password matches
            if (password == confirmPassword){
                MyApi().register(RegisterUser(email, name, password, mode))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<RegisterResponse>() {
                        override fun onSuccess(response: RegisterResponse) {
                            if (response != null){
                                if (response.data != null) {
                                    registerResponse.value = response
                                }
                            }
                            context.d(response.toString())
                        }

                        override fun onError(t: Throwable) {
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