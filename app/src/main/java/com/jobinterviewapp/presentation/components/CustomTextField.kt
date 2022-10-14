package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.weatherapp.core.util.UiText

@Composable
fun CustomTextField(
    value: String,
    helper: UiText?,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    helperColor: Color = MaterialTheme.colors.secondary,
) {
    Column(
        modifier = modifier,
    ) {

        TextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = label,
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
        )

        helper?.let {
            Text(
                text = helper.asString(),
                fontSize = 14.sp,
                color = helperColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
