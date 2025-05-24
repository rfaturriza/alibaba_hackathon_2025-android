package com.kajianhub.alibabahackathon2025.service.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val client = OkHttpClient.Builder().build()

    val api: ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://your-domain.com/api") // Replace with actual domain
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }
}