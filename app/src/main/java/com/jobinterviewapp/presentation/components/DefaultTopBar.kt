package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: @Composable () -> Unit,
    actions: @Composable (RowScope.() -> Unit) = {},
    navigationIcon: @Composable () -> Unit = {},
) {
    Column {
        CenterAlignedTopAppBar(
            title = title,
            actions = actions,
            navigationIcon = navigationIcon,
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 12.dp),
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}