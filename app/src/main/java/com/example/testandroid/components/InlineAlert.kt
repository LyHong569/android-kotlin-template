package com.example.testandroid.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class AlertType {
    ERROR, WARNING, INFO, SUCCESS, CAUTION
}

@Composable
fun InlineAlert(description: String? = null, type: AlertType = AlertType.ERROR) {

    val color = when (type) {
        AlertType.ERROR -> MaterialTheme.colorScheme.error
        AlertType.WARNING -> Color.Yellow
        AlertType.INFO -> Color.Blue
        AlertType.SUCCESS -> Color.Green
        AlertType.CAUTION -> Color.Gray
    }

    val message = description ?: when (type) {
        AlertType.ERROR -> "Something went wrong, please try again"
        AlertType.WARNING -> "Warning"
        AlertType.INFO -> "Information"
        AlertType.SUCCESS -> "Success"
        AlertType.CAUTION -> "Caution"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Icon(
//                imageVector = Icons.Default.Info,
//                contentDescription = "Warning",
//                tint = MaterialTheme.colorScheme.error,
//                modifier = Modifier.size(20.dp)
//            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}
