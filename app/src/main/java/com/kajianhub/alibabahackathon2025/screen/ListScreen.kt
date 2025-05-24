package com.kajianhub.alibabahackathon2025.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.kajianhub.alibabahackathon2025.FoodItemData

@Composable
fun ListFoodScreen(onFoodClick: (String) -> Unit){
    FoodItemListScreen(){ foodName ->
        onFoodClick(foodName)
    }
}




@Composable
fun FoodItemCard(
    foodName: String,
    foodDetail: String,
    originalPrice: String,
    discountedPrice: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onFoodClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onFoodClick(foodName)
            }
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {

            // 🔹 Left Column - Texts
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = foodName,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = foodDetail,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Show original price conditionally
                if (discountedPrice.isNotEmpty()) {
                    Text(
                        text = "Rp $originalPrice",
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    Text(
                        text = "Rp $originalPrice",
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Show discounted price and Promo button only if discount exists
                if (discountedPrice.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Rp $discountedPrice",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { /* Handle promo click */ },
                            shape = RoundedCornerShape(30.dp),
                        ) {
                            Text(text = "Promo")
                        }
                    }
                }
            }

            // 🔹 Right Side - Image & Add Button
            Box {
                val imageSize = 150.dp


                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )


            }

        }
    }
}

@Composable
fun FoodItemListScreen(onFoodClick: (String) -> Unit) {
    val items = listOf(
        FoodItemData(
            name = "Spicy Chicken Burger",
            detail = "With lettuce, tomato, and special sauce",
            originalPrice = "50000",
            discountedPrice = "40000",
            imageUrl = "https://i.pinimg.com/736x/e1/c2/4c/e1c24cf335a87ccbec1ca421f80f015d.jpg "
        ),
        FoodItemData(
            name = "Cheeseburger",
            detail = "Classic cheeseburger with grilled beef",
            originalPrice = "35000",
            discountedPrice = "",
            imageUrl = "https://i.pinimg.com/736x/e1/c2/4c/e1c24cf335a87ccbec1ca421f80f015d.jpg "
        ),
        FoodItemData(
            name = "Double Bacon Burger",
            detail = "Double meat, double bacon",
            originalPrice = "60000",
            discountedPrice = "50000",
            imageUrl = "https://i.pinimg.com/736x/e1/c2/4c/e1c24cf335a87ccbec1ca421f80f015d.jpg "
        ),
        FoodItemData(
            name = "Veggie Wrap",
            detail = "Fresh veggies and hummus wrap",
            originalPrice = "25000",
            discountedPrice = "",
            imageUrl = "https://i.pinimg.com/736x/e1/c2/4c/e1c24cf335a87ccbec1ca421f80f015d.jpg "
        ),
        FoodItemData(
            name = "Fries Large",
            detail = "Crispy golden fries",
            originalPrice = "15000",
            discountedPrice = "",
            imageUrl = "https://i.pinimg.com/736x/e1/c2/4c/e1c24cf335a87ccbec1ca421f80f015d.jpg "
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item(){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                // Your other content here (like price text)

                Button(
                    onClick = { /* Handle promo click */ },
                    shape = RoundedCornerShape(30.dp),
                ) {
                    Text(text = "Promo")
                }
            }
        }
        items(items.size) { index ->
            val item = items[index]
            FoodItemCard(
                foodName = item.name,
                foodDetail = item.detail,
                originalPrice = item.originalPrice,
                discountedPrice = item.discountedPrice,
                imageUrl = item.imageUrl,
                onFoodClick = { foodName ->
                    onFoodClick(foodName)
                }
            )
        }
    }
}