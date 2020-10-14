package com.example.propertymanagementapp.ui.properties

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.propertymanagementapp.data.repo.PropertyRepo
import com.example.propertymanagementapp.helpers.SessionManager

class AddPropertyViewModel: ViewModel(){
    var addPropertyListener: AddPropertyListener? = null

    var address: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
    var purchasePrice: String? = null
    var longitude: String? = null
    var latitude: String? = null
    var image: String = "https://insidelatinamerica.net/wp-content/uploads/2018/01/noImg_2.jpg"

    fun onSubmitButtonClick(view: View){
        val sessionManager = SessionManager(view.context)
        if (address.isNullOrEmpty() || city.isNullOrEmpty() ||
            state.isNullOrEmpty() || country.isNullOrEmpty() ||
            purchasePrice.isNullOrEmpty() || longitude.isNullOrEmpty() ||
            latitude.isNullOrEmpty() || sessionManager.getUserId().isEmpty() ||
            sessionManager.getUserType().isEmpty()){
            // failure
            addPropertyListener?.failure("Big oof")
            return
        }
        // success
        val addPropertyResponse = PropertyRepo().addProperty(view.context, address!!, city!!, country!!, image!!, latitude!!, longitude!!, purchasePrice!!, state!!, sessionManager.getUserId(), sessionManager.getUserType())
        addPropertyListener?.onSuccess(addPropertyResponse)
    }

    fun addImage(imageURL: String){
        image = imageURL
    }

}