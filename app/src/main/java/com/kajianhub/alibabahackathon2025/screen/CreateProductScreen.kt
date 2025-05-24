package com.kajianhub.alibabahackathon2025.screen

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import com.kajianhub.alibabahackathon2025.service.api.ProductUploader
import kotlinx.coroutines.launch

@Composable
fun CreateProductScreen(lifecycleScope: LifecycleCoroutineScope) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var merchantId by remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = merchantId,
            onValueChange = { merchantId = it },
            label = { Text("Merchant ID (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Add Image Picker Logic (depends on how you pick images)

        Button(onClick = {
            lifecycleScope.launch {
                val result = ProductUploader.uploadProduct(
                    title = title,
                    description = description,
                    price = price.toIntOrNull() ?: 0,
                    merchantId = merchantId.takeIf { it.isNotBlank() },
                    imageFiles = listOf() // Replace with real File objects
                )

//                when (result) {
//                    is Result.Success -> {
//                        Toast.makeText(LocalContext.current, "Product created!", Toast.LENGTH_SHORT).show()
//                    }
//                    is Result.Failure -> {
//                        Toast.makeText(LocalContext.current, "Error: ${result.exception.message}", Toast.LENGTH_LONG).show()
//                    }
//                }
            }
        }) {
            Text("Upload Product")
        }
    }
}