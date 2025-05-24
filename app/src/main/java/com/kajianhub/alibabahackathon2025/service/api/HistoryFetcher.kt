package com.kajianhub.alibabahackathon2025.service.api

import androidx.lifecycle.LifecycleCoroutineScope
import com.kajianhub.alibabahackathon2025.service.model.OrderHistoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object HistoryFetcher {

    private val _histories = MutableStateFlow<List<OrderHistoryItem>>(emptyList())
    val histories: StateFlow<List<OrderHistoryItem>> get() = _histories

    fun fetchOrderHistories(lifecycleScope: LifecycleCoroutineScope, userId: String) {
        lifecycleScope.launch {
            try {
                val items = withContext(Dispatchers.IO) {
                    RetrofitClient.api.getOrderHistories(userId).data
                }
                _histories.value = items
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}