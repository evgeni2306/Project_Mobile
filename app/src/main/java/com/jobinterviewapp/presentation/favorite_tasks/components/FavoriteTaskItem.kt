package com.jobinterviewapp.presentation.favorite_tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
fun FavoriteTaskItem(
    task: FavoriteTask,
    onDeleteTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
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
            IconButton(
                onClick = onDeleteTaskClick,
                modifier = Modifier
                    .size(33.dp)
                ,
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
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