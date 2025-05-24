package com.kajianhub.alibabahackathon2025.service.api

import com.kajianhub.alibabahackathon2025.service.model.OrderErrorResponse
import com.kajianhub.alibabahackathon2025.service.model.OrderRequest
import com.kajianhub.alibabahackathon2025.service.model.OrderSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import retrofit2.HttpException
import java.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement


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
                    val body = response.body() ?: return@withContext OrderResult.Error(IOException("Empty response"))

                    val json = Json.decodeFromJsonElement<JsonObject>(body as JsonObject)

                    if (json.containsKey("alert")) {
                        val alertResponse = Json.decodeFromJsonElement<OrderErrorResponse>(body)
                        OrderResult.Alert(alertResponse)
                    } else {
                        val successResponse = Json.decodeFromJsonElement<OrderSuccessResponse>(body)
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