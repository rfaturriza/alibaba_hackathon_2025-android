package com.kajianhub.alibabahackathon2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kajianhub.alibabahackathon2025.screen.DetailFoodScreen
import com.kajianhub.alibabahackathon2025.screen.HistoryScreen
import com.kajianhub.alibabahackathon2025.screen.ListFoodScreen
import com.kajianhub.alibabahackathon2025.screen.OrderConfirmationScreen
import com.kajianhub.alibabahackathon2025.screen.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}


@Composable
fun MyApp() {
    val navController = rememberNavController()
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    NavHost(navController, startDestination = Screen.ListFoodScreen.route) {
        composable(Screen.ListFoodScreen.route) {
            ListFoodScreen(lifecycleScope = lifecycleScope, onHistoryClick = { id ->
                navController.navigate("history/$id")
            }) { foodId ->
                navController.navigate(Screen.DetailFoodScreen.createRoute(foodId))
            }
        }

        composable(
            route = Screen.DetailFoodScreen.route,
            arguments = listOf(navArgument("foodId") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            DetailFoodScreen(lifecycleScope = lifecycleScope, foodId = foodId) {
                navController.navigate(Screen.ListFoodScreen.route)
            }

        }

        composable(route = "order_confirmation_success") {
            OrderConfirmationScreen(popupType = "success", onDismiss = {
                navController.navigate(Screen.ListFoodScreen.route)
            })
        }

        composable(route = "order_confirmation_calorie") {
            OrderConfirmationScreen(popupType = "calorie", onDismiss = {
                navController.navigate(Screen.ListFoodScreen.route)
            })
        }

        composable(route = "history/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HistoryScreen(lifecycleScope = lifecycleScope, userId = userId)
        }
    }
}


// Data class for food item
data class FoodItemData(
    val name: String,
    val detail: String,
    val originalPrice: String,
    val discountedPrice: String,
    val imageUrl: String
)


data class FoodDetail(
    val deliveryAddress: String,
    val itemTitle: String,
    val itemDescription: String,
    val originalPrice: String,
    val discountedPrice: String? = null,
    val paymentMethodIcon: ImageVector,
    val paymentMethodName: String,
    val paymentAmount: String,
    val quantity: Int = 1
)

