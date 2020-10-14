package com.example.propertymanagementapp.data.network

import com.example.propertymanagementapp.helpers.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object ApiWorker{

    private const val REQUEST_TIMEOUT: Long = 60

    private var okHttpClient: OkHttpClient? = null

    private var TOKEN = SessionManager.TOKEN

    val client: OkHttpClient
    get(){
        if (okHttpClient == null){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            var httpBuilder = OkHttpClient.Builder();
            httpBuilder
                .connectTimeout(REQUEST_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    val original = chain!!.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer $TOKEN"
                        )
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
            okHttpClient = httpBuilder.build()
        }
        return okHttpClient!!
    }

}