package com.jobinterviewapp.presentation.authorization.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.core.util.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTextField(
    value: String,
    helper: UiText?,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        isError = helper != null,
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = label,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions.Companion.Default,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        supportingText = {
            helper?.let {
                Text(
                    text = helper.asString(),
                )
            }
        }
    )
}
