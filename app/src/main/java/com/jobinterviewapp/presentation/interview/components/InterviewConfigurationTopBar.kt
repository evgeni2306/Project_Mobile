package com.jobinterviewapp.presentation.interview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewConfigurationTopBar(
    navController: NavController,
    screen: Screen,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = screen.screenName.asString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                )
            }
        },
        navigationIcon = {
            if(screen.route != Screen.FieldsOfActivityScreen.route) {
                IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
            }
        }
    )
}