package com.jobinterviewapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.home.components.FieldOfActivityList
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.home.components.ScreenPlaceholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldsOfActivityScreen(
    navController: NavController,
    viewModel: FieldsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        color = MaterialTheme.colors.onPrimary,
                        text = Screen.FieldsOfActivityScreen.screenName.asString(),
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
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenPlaceholder(stringResource(id = R.string.field_of_activity_screen_placeholder))
                FieldOfActivityList(
                    listState = state.fieldsOfActivity,
                    onItemClick = {
                        navController.navigate(Screen.DirectionsOfFieldScreen.withArgs(it.id.toString()))
                    }
                )
            }
        }
    }
}