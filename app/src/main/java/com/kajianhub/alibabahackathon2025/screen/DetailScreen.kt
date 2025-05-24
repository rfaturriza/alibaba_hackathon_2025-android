package com.kajianhub.alibabahackathon2025.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Note
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kajianhub.alibabahackathon2025.FoodDetail

@Composable
fun DetailFoodScreen(foodId: String, onClick: (Boolean) -> Unit){
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
        onPurchaseClick = {
            onClick(false)
        }
    )
}

@Composable
fun FoodDetailItem(
    foodDetail: FoodDetail,
    onLocationChangeClick: () -> Unit = {},
    onAddAddressDetailsClick: () -> Unit = {},
    onNotesClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onQuantityChange: (Int) -> Unit = {},
    onPurchaseClick: () -> Unit = {}
) {
    var quantity by remember { mutableIntStateOf(foodDetail.quantity) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔹 SECTION 1: Delivery Location Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Delivery location", fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = foodDetail.deliveryAddress,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    OutlinedButton(
                        onClick = onLocationChangeClick,
                        border = BorderStroke(1.dp, Color.Green),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Change location", color = Color.Green)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // ⚠️ Warning Panel
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Yellow, CircleShape)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {
                        Text(text = "!", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add address details to help drivers find your location faster",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 📍 Add Address / Notes Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ElevatedButton(onClick = onAddAddressDetailsClick) {
                        Icon(imageVector = Icons.Default.Map, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Add address details")
                    }
                    ElevatedButton(onClick = onNotesClick) {
                        Icon(imageVector = Icons.Default.Note, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Notes")
                    }
                }
            }
        }

        // 🔹 SECTION 2: Item Info
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = foodDetail.itemTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = foodDetail.itemDescription,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!foodDetail.discountedPrice.isNullOrEmpty()) {
                Text(
                    text = "Rp ${foodDetail.originalPrice}",
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            } else {
                Text(text = "Rp ${foodDetail.originalPrice}")
            }

            if (!foodDetail.discountedPrice.isNullOrEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Rp ${foodDetail.discountedPrice}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = { /* Handle promo click */ }) {
                        Text(text = "Promo")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Edit + Quantity Controls
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Edit")
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, CircleShape)
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    IconButton(onClick = {
                        if (quantity > 1) {
                            quantity--
                            onQuantityChange(quantity)
                        }
                    }) {
                        Text(text = "-")
                    }
                    Text(text = "$quantity", modifier = Modifier.padding(horizontal = 8.dp))
                    IconButton(onClick = {
                        quantity++
                        onQuantityChange(quantity)
                    }) {
                        Text(text = "+")
                    }
                }
            }
        }

        // 🔹 SECTION 3: Payment + Action Button
        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(imageVector = foodDetail.paymentMethodIcon, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "${foodDetail.paymentMethodName} • Rp ${foodDetail.paymentAmount}")
        }

        Button(
            onClick = onPurchaseClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
        ) {
            Text(text = "Purchase and deliver now", color = Color.White)
        }
    }
}