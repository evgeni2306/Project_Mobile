package com.jobinterviewapp.presentation.interview.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.unit.dp

@Composable
fun TaskCategoryElement(
    categoryName: String,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp)
    ) {
        Box(
            modifier = modifier.padding(3.dp)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = SemiBold,
            )
        }
    }
}