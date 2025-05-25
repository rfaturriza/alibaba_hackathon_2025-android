package com.kajianhub.alibabahackathon2025.service.model

import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
    val message: String,
    val data: List<FoodItem>
)

@Serializable
data class FoodItem(
    val id: String,                 // Also present in JSON (can be same or different)
    val images_url: List<String>,
    val title: String,
    val description: String,
    val price: Int,
    val nutrition: NutritionInfo,
    val merchant_id: String?,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class NutritionInfo(
    val calory: String,
    val protein: String,
    val carbohydrate: String,
    val fat: String,
    val sugar: String,
    val fiber: String,
    val allergen_potential: String
)


//

@Serializable
data class FoodItemResponse(
    val message: String,
    val data: FoodItem
)


@Serializable
data class OrderRequest(
    val product_id: String,
    val user_id: String,
    val force_order: Boolean? = null // Optional, default not sent
)

@Serializable
data class OrderSuccessResponse(
    val message: String,
    val data: OrderData? = null // Nullable to handle cases where data might not be present
)

@Serializable
data class OrderData(
    val order_id: String,
    val product: FoodItem,
    val user_id: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class OrderErrorResponse(
    val alert: Boolean,
    val message: String,
    val exceeded: List<NutrientExceeded>? = null
)

@Serializable
data class NutrientExceeded(
    val param: String,
    val total: Int,
    val limit: Int
)


@Serializable
data class OrderHistoryResponse(
    val message: String,
    val data: List<OrderHistoryItem>
)

@Serializable
data class OrderHistoryItem(
    val order_id: String,
    val title: String,
    val description: String,
    val price: Int,
    val product: FoodItem,
    val user_id: String,
    val createdAt: String,
    val updatedAt: String
)

// Reuse existing FoodItem class