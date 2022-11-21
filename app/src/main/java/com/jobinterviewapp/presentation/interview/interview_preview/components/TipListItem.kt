package com.jobinterviewapp.presentation.interview.interview_preview.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipListItem(
    @DrawableRes trailingImageId: Int,
    title: String,
    description: String,
    animationDuration: Int,
) {
    val isVisible = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            this.targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(animationDuration),
            initialOffsetX = {
                it
            }
        )
    ) {
        ListItem(
            headlineText = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            painter = painterResource(id = trailingImageId),
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = description,
                    )
                }
            },
        )
    }
}