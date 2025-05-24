package com.kajianhub.alibabahackathon2025.service.api

import androidx.lifecycle.LifecycleCoroutineScope
import com.kajianhub.alibabahackathon2025.service.model.FoodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object FoodFetcher {

    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> get() = _foodItems

    fun fetchFoodItems(lifecycleScope: LifecycleCoroutineScope) {
        lifecycleScope.launch {
            try {
                val items = withContext(Dispatchers.IO) {
                    RetrofitClient.api.getProducts()
                }
                _foodItems.value = items
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}