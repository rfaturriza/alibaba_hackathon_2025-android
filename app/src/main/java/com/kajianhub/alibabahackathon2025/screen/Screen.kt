package com.kajianhub.alibabahackathon2025.screen

sealed class Screen(val route: String) {

    object ListFoodScreen : Screen("list_food")
    object DetailFoodScreen: Screen("detail_food/{foodId}"){
        fun createRoute(foodId: String) = "detail_food/$foodId"
    }
    object OrderScreen: Screen("order_screen")
}

