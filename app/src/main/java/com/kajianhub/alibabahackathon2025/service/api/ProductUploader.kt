package com.kajianhub.alibabahackathon2025.service.api

import com.kajianhub.alibabahackathon2025.screen.prepareImageFiles
import com.kajianhub.alibabahackathon2025.service.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object ProductUploader {

    suspend fun uploadProduct(
        title: String,
        description: String,
        price: Int,
        merchantId: String?,
        imageFiles: List<File>
    ): Result<FoodItem> = withContext(Dispatchers.IO) {
        try {
            val api = RetrofitClient.api

            val titleBody = title.toRequestBody("text/plain".toMediaType())
            val descBody = description.toRequestBody("text/plain".toMediaType())
            val priceBody = price.toString().toRequestBody("text/plain".toMediaType())
            val merchantBody = merchantId?.toRequestBody("text/plain".toMediaType())

            val imageParts = prepareImageFiles(imageFiles)

            val response = api.createProduct(
                title = titleBody,
                description = descBody,
                price = priceBody,
                merchantId = merchantBody,
                images = imageParts
            )

            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}