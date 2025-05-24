package com.kajianhub.alibabahackathon2025.service.api

import com.kajianhub.alibabahackathon2025.service.model.FoodItem
import com.kajianhub.alibabahackathon2025.service.model.FoodItemResponse
import com.kajianhub.alibabahackathon2025.service.model.OrderHistoryResponse
import com.kajianhub.alibabahackathon2025.service.model.OrderRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProductApi {
    @GET("/products")
    suspend fun getProducts(): List<FoodItem>

    @Multipart
    @POST("/products")
    suspend fun createProduct(
        @Part("title") title: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("merchant_id") merchantId: RequestBody? = null,

        @Part images: List<MultipartBody.Part>
    ): FoodItemResponse


    @POST("/order")
    suspend fun placeOrder(@Body request: OrderRequest): retrofit2.Response<Any>

    @GET("/histories")
    suspend fun getOrderHistories(@Query("user_id") userId: String): OrderHistoryResponse
}