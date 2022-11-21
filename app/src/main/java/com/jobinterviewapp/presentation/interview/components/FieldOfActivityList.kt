package com.jobinterviewapp.presentation.interview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldOfActivityList(
    listState: List<FieldOfActivityDto>,
    onItemClick: (FieldOfActivityDto) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(listState) { fieldOfActivity ->
            ListItem(
                modifier = Modifier
                    .padding(vertical = 0.dp)
                    .clickable {
                        onItemClick(fieldOfActivity)
                    }
                    .padding(vertical = 0.dp)
                ,
                headlineText = {
                    Text(
                        text = fieldOfActivity.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                leadingContent = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_field_of_activity),
                        contentDescription = null
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
