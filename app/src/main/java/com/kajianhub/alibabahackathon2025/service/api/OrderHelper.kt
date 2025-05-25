package com.kajianhub.alibabahackathon2025.service.api

import com.kajianhub.alibabahackathon2025.service.model.OrderData
import com.kajianhub.alibabahackathon2025.service.model.OrderErrorResponse
import com.kajianhub.alibabahackathon2025.service.model.OrderRequest
import com.kajianhub.alibabahackathon2025.service.model.OrderSuccessResponse
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import java.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


object OrderHelper {

    sealed class OrderResult {
        data class Success(val response: OrderSuccessResponse) : OrderResult()
        data class Alert(val response: OrderErrorResponse) : OrderResult()
        data class Error(val exception: Exception) : OrderResult()
    }

    suspend fun placeOrder(
        productId: String,
        userId: String,
        forceOrder: Boolean = false
    ): OrderResult = withContext(Dispatchers.IO) {
        try {
            val api = RetrofitClient.api
            val response = api.placeOrder(
                OrderRequest(productId, userId, if (forceOrder) true else null)
            )

            when (response.code()) {
                200, 201 -> {
                    val body = response.body()
                        ?: return@withContext OrderResult.Error(IOException("Empty response"))

                    // Convert the response body (Map or Any) to JSON string using Moshi
                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(Any::class.java)
                    val jsonString = adapter.toJson(body)
                    val jsonElement = Json.parseToJsonElement(jsonString)
                    val json = jsonElement.jsonObject

                    if (json.containsKey("alert") && json["alert"]?.jsonPrimitive?.booleanOrNull == true) {
                        // Only decode the relevant part for alert
                        val alertResponse = OrderErrorResponse(
                            message = json["message"]?.jsonPrimitive?.content ?: "Unknown error",
                            alert = json["alert"]?.jsonPrimitive?.booleanOrNull ?: false,
                        )
                        OrderResult.Alert(alertResponse)
                    } else {
                        // Only decode the relevant part for success
                        val successResponse = OrderSuccessResponse(
                            message = json["message"]?.jsonPrimitive?.content ?: "Order placed successfully",

                        )
                        OrderResult.Success(successResponse)
                    }
                }

                else -> OrderResult.Error(IOException("Unexpected HTTP code: ${response.code()}"))
            }
        } catch (e: HttpException) {
            OrderResult.Error(e)
        } catch (e: IOException) {
            OrderResult.Error(e)
        } catch (e: Exception) {
            OrderResult.Error(e)
        }
    }
}

