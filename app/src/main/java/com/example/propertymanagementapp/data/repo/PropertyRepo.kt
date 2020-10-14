package com.example.propertymanagementapp.data.repo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.propertymanagementapp.data.model.*
import com.example.propertymanagementapp.data.network.MyApi
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PropertyRepo{
    @SuppressLint("CheckResult")
    fun getProperties(context: Context): LiveData<PropertyResponse>{
        val sessionManager = SessionManager(context)
        val propertyResponse = MutableLiveData<PropertyResponse>()
        MyApi().getProperties().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<PropertyResponse>(){
                override fun onSuccess(response: PropertyResponse) {
                    if (response.data != null)
                    {
                        propertyResponse.value = response
                    }
                }

                override fun onError(e: Throwable) {
                    context.d(e.message.toString())
                }

            })
        return propertyResponse
    }

    @SuppressLint("CheckResult")
    fun addProperty(context: Context, address: String, city: String, country: String, image: String,
                    longitude: String, latitude: String, purchasePrice: String, state: String, userId: String,
                    userType: String): LiveData<AddPropertyResponse>{
        val addPropertyResponse = MutableLiveData<AddPropertyResponse>()
        MyApi().addProperty(AddProperty(address, city, country, image, latitude,
                longitude, purchasePrice, state,
                userId, userType))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
            object: DisposableSingleObserver<AddPropertyResponse>(){
                override fun onSuccess(response: AddPropertyResponse) {
                    addPropertyResponse.value = response
                    context.d(response.toString())
                }

                override fun onError(e: Throwable) {
                    context.d(e.message.toString())
                }

            })
        return addPropertyResponse
    }


}