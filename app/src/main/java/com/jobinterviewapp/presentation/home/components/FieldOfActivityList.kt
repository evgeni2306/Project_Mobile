package com.jobinterviewapp.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto


@Composable
fun FieldOfActivityList(
    listState: List<FieldOfActivityDto>
) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(listState) { fieldOfActivity ->
            val shape = RoundedCornerShape(5.dp)
            Card(
                shape = shape,
                modifier = Modifier
                    .padding(vertical = 3.dp, horizontal = 6.dp)
                    .clip(shape)
                    .clickable {

                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_field_of_activity),
                            contentDescription = null
                        )
                        Spacer(Modifier.width(13.dp))
                        Text(
                            text = fieldOfActivity.name,
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
    }
}
