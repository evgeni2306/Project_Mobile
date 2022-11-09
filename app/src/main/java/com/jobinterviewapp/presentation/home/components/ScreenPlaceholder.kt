package com.jobinterviewapp.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jobinterviewapp.R

@Composable
fun ScreenPlaceholder(text: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp),
        text = text,
        fontSize = 22.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
    )
}