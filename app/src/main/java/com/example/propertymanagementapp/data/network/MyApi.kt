package com.example.propertymanagementapp.data.network

import com.example.propertymanagementapp.app.Config
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.data.model.RegisterResponse
import com.example.propertymanagementapp.data.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {
    @POST("auth/login")
    fun login(@Body user: User): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body user: User): Call<RegisterResponse>

    fun getProducts()

    @GET("category/{empId}")
    fun getProductById(@Query("id") empId: Int){

    }

    // api/category?it=10 --- query string
    @PUT("category/{id}")
    fun updateUser(@Body user: User, @Query("id") updateId: Int)

    @PUT("category/{id}")
    fun deleteUser(@Path("id") id: Int)

    companion object{
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)

        }
    }
}