package com.example.propertymanagementapp.data.model

data class PropertyResponse(
    val count: Int,
    val data: ArrayList<Property>,
    val error: Boolean
)