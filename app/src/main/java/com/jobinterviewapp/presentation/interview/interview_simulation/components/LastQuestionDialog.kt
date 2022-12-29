package com.jobinterviewapp.presentation.interview.interview_simulation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen

@Composable
fun LastQuestionDialog(
    onResultsClicked: () -> Unit,
) {
    Text(
        text = stringResource(R.string.end_of_interview_text),
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(Modifier.height(10.dp))
    Button(
        onClick = onResultsClicked,
    ) {
        Text(
            text = stringResource(R.string.interview_result_button_text),
        )
    }
}