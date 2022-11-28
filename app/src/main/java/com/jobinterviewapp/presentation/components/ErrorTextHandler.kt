package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.UiText

@Composable
fun ErrorTextHandler(
    error: UiText?,
    modifier: Modifier,
    onRefreshClick: () -> Unit
) {
    error?.let { errorMsg ->
        Column(
            modifier = modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMsg.asString(),
                textAlign = TextAlign.Center,
            )
            TextButton(
                onClick = onRefreshClick,
                ) {
                Text(
                    text = stringResource(R.string.refresh_page_button_text),
                )
            }
        }
    }
}