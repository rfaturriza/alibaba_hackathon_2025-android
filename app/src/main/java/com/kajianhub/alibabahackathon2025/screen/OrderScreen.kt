package com.kajianhub.alibabahackathon2025.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed class PopupState {
    data object Hidden : PopupState()
    data class Shown(val icon: String, val message: String) : PopupState()
}

@Composable
fun OrderConfirmationScreen(
    popupType: String = "success", // Can be "success" or "calorie"
    onDismiss: () -> Unit
) {
    val popupState by remember {
        mutableStateOf<PopupState>(
            when (popupType) {
                "calorie" -> PopupState.Shown("‼️", "Your calorie already exceeds daily limit.")
                else -> PopupState.Shown("✅", "You successfully ordered!")
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Thank you for your order!", fontSize = 20.sp, modifier = Modifier.align(
            Alignment.Center))
    }

    DynamicPopup(
        popupState = popupState,
        onDismiss = { onDismiss() }
    )
}

@Composable
fun DynamicPopup(
    popupState: PopupState,
    onDismiss: () -> Unit
) {
    if (popupState is PopupState.Shown) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = popupState.icon,
                    fontSize = 30.sp
                )
            },
            text = {
                Text(text = popupState.message)
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "Close")
                }
            }
        )
    }
}