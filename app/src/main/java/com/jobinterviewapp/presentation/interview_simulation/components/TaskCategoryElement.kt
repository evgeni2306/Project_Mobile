package com.jobinterviewapp.presentation.interview_simulation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaskCategoryElement(
    categoryName: String,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = modifier.padding(4.dp)
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.titleSmall,
            )
        }
    }
}