package com.example.testandroid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(
    title: String,
    description: String?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    hasDismissButton: Boolean = true,
    confirmText: String = "Confirm",
    dismissText: String = "Not Now",
) {
    Dialog(
        onDismissRequest = { if (hasDismissButton) onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = hasDismissButton,
            dismissOnClickOutside = hasDismissButton
        )
    ) {
        DialogContent(
            title = title,
            description = description,
            onDismiss = onDismiss,
            onConfirm = onConfirm,
            hasDismissButton = hasDismissButton,
            confirmText = confirmText,
            dismissText = dismissText
        )
    }
}

@Composable
private fun DialogContent(
    title: String,
    description: String?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    hasDismissButton: Boolean,
    confirmText: String,
    dismissText: String,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (!description.isNullOrEmpty()) {
                    Text(
                        text = description,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 8.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (hasDismissButton) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = dismissText,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 5.dp)
                        )
                    }
                }

                TextButton(onClick = onConfirm) {
                    Text(
                        text = confirmText,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }
    }
}