package com.jobinterviewapp.presentation.interview.interview_configuration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.presentation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewConfigurationTopBar(
    navController: NavController,
    screen: Screen,
) {
    val context = LocalContext.current
    val localLifecycleOwner = LocalLifecycleOwner.current
    Column {
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
                    onClick = {
                        localLifecycleOwner.lifecycleScope.launch {
                            AppModule.DataStoreManager(context).setAuthStatus(
                                authorized = false
                            )
                        }
                        navController.navigate(Screen.RegistrationScreen.route) {
                            popUpTo(Screen.KnowledgeBaseScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_profile),
                        contentDescription = null,
                    )
                }
            },
            navigationIcon = {
                if(screen.route != Screen.SavedProfessionsScreen.route) {
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
        Divider(
            modifier = Modifier
                .padding(horizontal = 12.dp),
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}