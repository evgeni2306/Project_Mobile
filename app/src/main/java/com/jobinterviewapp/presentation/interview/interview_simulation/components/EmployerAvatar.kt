package com.jobinterviewapp.presentation.interview.interview_simulation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R

@Composable
fun EmployerAvatar() {
    Image(
        modifier = Modifier.size(90.dp),
        painter = painterResource(id = R.drawable.ic_employer),
        contentDescription = null,
    )
}