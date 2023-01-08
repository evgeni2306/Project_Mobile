package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jobinterviewapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: @Composable () -> Unit,
    onProfileClick: () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
) {
    Column {
        CenterAlignedTopAppBar(
            title = title,
            actions = {
                IconButton(
                    onClick = onProfileClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                    )
                }
            },
            navigationIcon = navigationIcon,
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 12.dp),
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}