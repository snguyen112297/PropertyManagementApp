package com.example.propertymanagementapp.data.network

import com.example.propertymanagementapp.app.Config
import com.example.propertymanagementapp.data.model.*
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {
    @POST("auth/login")
    fun login(@Body user: User): Single<LoginResponse>

    @POST("auth/register")
    fun register(@Body user: RegisterUser): Single<RegisterResponse>

    @GET("property")
    fun getProperties(): Single<PropertyResponse>

    @POST("property")
    fun addProperty(@Body addProperty: AddProperty): Single<AddPropertyResponse>

    @GET("category/{empId}")
    fun getProductById(@Query("id") empId: Int){

    }

    @Multipart
    @POST("upload/property/picture")
    fun uploadImage(@Part image: MultipartBody.Part): Call<ImageUploadResponse>

    // api/category?it=10 --- query string
    @PUT("category/{id}")
    fun updateUser(@Body user: User, @Query("id") updateId: Int)

    @PUT("category/{id}")
    fun deleteUser(@Path("id") id: Int)

    companion object{
        operator fun invoke(): MyApi {
            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(ApiWorker.client)
                .build()
                .create(MyApi::class.java)

        }
    }
}