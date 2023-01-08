package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@Composable
fun TaskItemCard(
    task: Task,
    onFavoriteTaskClicked: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(bottom = 8.dp)
        ,
    ) {
        Column(
            modifier = Modifier
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
                    onClick = onFavoriteTaskClicked,
                    modifier = Modifier
                        .size(33.dp)
                        ,
                ) {
                    Icon(
                        painter = if(task.isFavorite)
                            painterResource(id = R.drawable.ic_favorite_filled)
                        else
                            painterResource(id = R.drawable.ic_favorite_border),
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