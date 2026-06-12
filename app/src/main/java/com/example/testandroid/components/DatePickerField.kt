package com.example.testandroid.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_PATTERN = "MM/dd/yyyy"

@Composable
fun DatePickerField(
    selectedDateMillis: Long?,
    onDateSelected: (Long?) -> Unit,
    props: TextInputFieldProps
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDateMillis?.let(::formatDate).orEmpty(),
        onValueChange = {},
        label = { Text(props.label) },
        placeholder = { Text(props.placeholder) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (up != null) showDialog = true
                }
            },
    )

    if (showDialog) {
        DatePickerModal(
            initialDateMillis = selectedDateMillis,
            onConfirm = {
                onDateSelected(it)
                showDialog = false
            },
            onDismiss = { showDialog = false },
        )
    }
}

@Composable
private fun DatePickerModal(
    initialDateMillis: Long?,
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val state = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(state.selectedDateMillis) }) {
                Text("OK", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.primary)
            }
        },
    ) {
        DatePicker(
            state = state,
            showModeToggle = false,
            modifier = Modifier.sizeIn(maxWidth = 350.dp),
        )
    }
}

private val dateFormatter: SimpleDateFormat =
    SimpleDateFormat(DATE_PATTERN, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

private fun formatDate(millis: Long): String = dateFormatter.format(Date(millis))