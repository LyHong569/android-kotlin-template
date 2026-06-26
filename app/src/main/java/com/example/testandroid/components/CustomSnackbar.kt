package com.example.testandroid.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.testandroid.cores.managers.SnackbarType

@Composable
fun CustomSnackBar(
    data: SnackbarData,
    type: SnackbarType,
    onAction: (() -> Unit)? = null
) {
    val (backgroundColor, icon) = when (type) {
        SnackbarType.Success -> MaterialTheme.colorScheme.primary to null
        SnackbarType.Error -> MaterialTheme.colorScheme.error to null
        SnackbarType.Warning -> Color(0xFFF59E0B) to null
        SnackbarType.Default -> MaterialTheme.colorScheme.inverseSurface to null
    }

    Snackbar(
        modifier = Modifier
            .padding(12.dp, 0.dp)
            .clickable { data.dismiss() },
        containerColor = backgroundColor,
        action = {
            data.visuals.actionLabel?.let { label ->
                TextButton(onClick = {
                    data.performAction()
                    onAction?.invoke()
                }) {
                    Text(label, color = Color.White)
                }
            }
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                // TODO: implement icons
            }
            Text(data.visuals.message, color = Color.White)
        }
    }
}