package com.example.propertymanagementapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

class RandomNumberViewModel: ViewModel(){
    var randomNumber: String? = null

    fun createNumber(){
        var random = Random()
        var number = random.nextInt(10-1)+1
        randomNumber = "Number $number"
    }

    fun getNumber(): String? {
        if (randomNumber == null)
            createNumber()
        return randomNumber
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "View model cleared")
    }
}