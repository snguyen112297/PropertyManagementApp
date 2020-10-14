package com.example.propertymanagementapp.data.model

data class User(
    var _id: String,
    var email: String,
    var name: String,
    var password: String,
    var type: String
)

data class RegisterUser(
    var email: String,
    var name: String,
    var password: String,
    var type: String
)