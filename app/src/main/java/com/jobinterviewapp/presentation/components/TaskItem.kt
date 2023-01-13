package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@Composable
fun TaskItem(
    task: Task,
    onFavoriteTaskClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            ,
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