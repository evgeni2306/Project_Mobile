package com.jobinterviewapp.presentation.interview.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenPlaceholder(
    text: String,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .height(120.dp)
        ,
        shape = RoundedCornerShape(
            20.dp
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 5.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                ,
                text = text,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
fun preview() {
    ScreenPlaceholder(
        text = "test text title of screen long text"
    )
}
