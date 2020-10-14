package com.example.propertymanagementapp.data.model

data class AddProperty(
    val address: String,
    val city: String,
    val country: String,
    val image: String,
    val latitude: String,
    val longitude: String,
    val purchasePrice: String,
    val state: String,
    val userId: String,
    val userType: String
)

data class AddPropertyResponse(
    val data: AddProperty,
    val error: Boolean,
    val message: String
)