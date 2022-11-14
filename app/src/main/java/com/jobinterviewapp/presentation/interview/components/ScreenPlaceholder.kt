package com.jobinterviewapp.presentation.interview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenPlaceholder(
    text: String,
    color: Color = MaterialTheme.colors.onBackground,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp),
        text = text,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = color,
    )
}