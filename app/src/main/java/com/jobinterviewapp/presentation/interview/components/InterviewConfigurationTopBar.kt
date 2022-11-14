package com.jobinterviewapp.presentation.interview.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                color = MaterialTheme.colors.onPrimary,
                text = screen.screenName.asString(),
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
            )
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                    tint = Color.LightGray,
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colors.primaryVariant
        ),
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
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
            }
//            backStackEntry.value?.destination?.route?.let {
//                if(it != Screen.FieldsOfActivityScreen.route) {
//                    IconButton(
//                        onClick = {
//                            navController.navigateUp()
//                        },
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = null,
//                            tint = MaterialTheme.colors.onPrimary,
//                        )
//                    }
//                }
//            }
//            if(backStackEntry.value?.destination?.route != Screen.FieldsOfActivityScreen.route) {
//                IconButton(
//                    onClick = {
//                        navController.navigateUp()
//                    },
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.ArrowBack,
//                        contentDescription = null,
//                        tint = MaterialTheme.colors.onPrimary,
//                    )
//                }
//            }
        }
    )
}