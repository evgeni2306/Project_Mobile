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
import com.jobinterviewapp.data.remote.dto.FavoriteTaskDto
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@Composable
fun FavoriteTaskCard(
    task: FavoriteTaskDto,
    onDeleteTaskClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp)
        ,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 9.dp,
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
                IconButton(
                    onClick = onDeleteTaskClick,
                    modifier = Modifier
                        .size(33.dp)
                    ,
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
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
}