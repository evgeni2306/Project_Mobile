package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.weatherapp.core.util.UiText

@Composable
fun AuthTextField(
    value: String,
    helper: UiText?,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions,
    singleLine: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    helperColor: Color = MaterialTheme.colors.primary,
) {
    Column(
        modifier = modifier,
    ) {

        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = label,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions.Companion.Default,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.background,
                unfocusedIndicatorColor = Color.LightGray),
        )

        helper?.let {
            Text(
                text = helper.asString(),
                fontSize = 12.sp,
                color = helperColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
