package com.example.propertymanagementapp.data.model

data class RegisterResponse(
    val data: User,
    val error: Boolean,
    val message: String
)

