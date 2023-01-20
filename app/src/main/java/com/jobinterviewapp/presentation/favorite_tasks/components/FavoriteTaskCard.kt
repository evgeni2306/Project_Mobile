package com.jobinterviewapp.presentation.favorite_tasks.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.domain.models.FavoriteTask
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@Composable
fun FavoriteTaskCard(
    task: FavoriteTask,
    modifier: Modifier = Modifier,
) {
    Column {
        val innerModifier = if (task.isSelected)
            Modifier.border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
        else
            Modifier
        ElevatedCard(
            modifier = innerModifier
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 20.dp,
                        start = 16.dp,
                        end = 16.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TaskCategoryElement(
                        categoryName = task.category,
                        modifier = Modifier,
                    )
                }
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = task.question,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.Start),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}