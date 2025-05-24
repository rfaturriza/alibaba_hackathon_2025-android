package com.kajianhub.alibabahackathon2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.kajianhub.alibabahackathon2025.screen.DetailFoodScreen
import com.kajianhub.alibabahackathon2025.screen.FoodDetailItem
import com.kajianhub.alibabahackathon2025.screen.FoodItemCard
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
fun MyApp(){
    val navController  = rememberNavController()
    NavHost(navController, startDestination = Screen.ListFoodScreen.route){
        composable(Screen.ListFoodScreen.route) {
            ListFoodScreen(){ foodId ->
                navController.navigate(Screen.DetailFoodScreen.createRoute(foodId))
            }
        }

        composable(
            route = Screen.DetailFoodScreen.route,
            arguments = listOf(navArgument("foodId") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
            DetailFoodScreen(foodId = foodId) { isSuccess ->
                if (isSuccess)  navController.navigate("order_confirmation_success") else navController.navigate("order_confirmation_calorie")
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



@Preview(showBackground = true)
@Composable
fun PreviewFoodDetailScreen() {
    val sampleData = FoodDetail(
        deliveryAddress = "Jalan Setiabudi Timur",
        itemTitle = "Spicy Chicken Burger",
        itemDescription = "With lettuce, tomato, and special sauce",
        originalPrice = "50000",
        discountedPrice = "40000",
        paymentMethodIcon = Icons.Default.AccountBalanceWallet,
        paymentMethodName = "GoPay",
        paymentAmount = "52.000"
    )

    FoodDetailItem(
        foodDetail = sampleData,
        onLocationChangeClick = { },
        onAddAddressDetailsClick = { },
        onNotesClick = { },
        onEditClick = { },
        onQuantityChange = { },
        onPurchaseClick = { }
    )
}


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

