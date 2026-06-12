package com.example.testandroid.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

data class TextInputFieldProps(
    val label: String = "",
    val placeholder: String = "",
    val errorMessage: String? = "",
    val enabled: Boolean = true,
    val readOnly: Boolean = false,
    val leadingIcon: (@Composable (() -> Unit))? = null,
    val trailingIcon: (@Composable (() -> Unit))? = null,
    val singleLine: Boolean = true,
    val modifier: Modifier = Modifier
        .fillMaxWidth(),
)

@Composable
fun TextInputField(value: String, onValueChange: (String) -> Unit, props: TextInputFieldProps) {
    Column {
        OutlinedTextField(
            value,
            onValueChange,
            label = { Text(props.label) },
            placeholder = { Text(props.placeholder) },
            isError = !props.errorMessage.isNullOrBlank(),

            modifier = props.modifier,

            enabled = props.enabled,
            readOnly = props.readOnly,

            leadingIcon = props.leadingIcon,
            trailingIcon = props.trailingIcon,

            singleLine = props.singleLine
        )

        if (!props.errorMessage.isNullOrBlank()) {
            Text(
                text = props.errorMessage,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

    }
}

@Composable
fun PasswordInputField(value: String, onValueChange: (String) -> Unit, props: TextInputFieldProps) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextInputField(
        value,
        onValueChange,
        props = TextInputFieldProps(
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    // TODO: add icons
//                    Icon(
//                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
//                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
//                    )
                }
            },
        )
    )
}

data class SelectOption(
    val label: String = "",
    val value: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectField(
    value: String?,
    onSelectChange: (String?) -> Unit,
    options: List<SelectOption>,
    isClearable: Boolean = true,
    props: TextInputFieldProps
) {
    var expanded by remember { mutableStateOf(false) }
    val displayText = remember(value, options) {
        options.find { it.value == value }?.label.orEmpty()
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier,
    ) {
        OutlinedTextField(
            value = displayText,
            onValueChange = {},
            readOnly = true,
            label = { Text(props.label) },
            placeholder = { Text(props.placeholder) },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isClearable && !value.isNullOrBlank()) {
                        IconButton(
                            onClick = {
                                onSelectChange(null)
                                expanded = false
                            },
                        ) {
                            // TODO: add icons with 18 dp size
                        }
                    }
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        onSelectChange(option.value)
                        expanded = false
                    },
                )
            }
        }
    }
}