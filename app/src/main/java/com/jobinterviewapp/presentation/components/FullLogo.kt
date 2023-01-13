package com.jobinterviewapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R

@Composable
fun FullLogo(
    modifier: Modifier,
    verticalPadding: Dp
) {
    Image(
        modifier = modifier
            .animateContentSize()
            .padding(vertical = verticalPadding)
            .fillMaxWidth()
            .height(49.dp)
        ,
        painter = painterResource(id = R.drawable.ic_full_logo),
        contentDescription = null,
    )
}