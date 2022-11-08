package com.jobinterviewapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jobinterviewapp.presentation.BottomNavItem

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier
            .height(50.dp)
            .shadow(4.dp),
        containerColor = backgroundColor,
    ) {
        items.forEach { item ->
            val selected = item.screen.route == backStackEntry.value?.destination?.route
                    || item.screen.subRoutes?.contains(backStackEntry.value?.destination?.route) ?: false
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemClick(item)
                },
                enabled = true,
                colors =  NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colors.background,
                ),
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            tint = if(selected) MaterialTheme.colors.primaryVariant else Color.Gray,
                            contentDescription = item.screen.screenName.asString()
                        )
                        Text(
                            text = item.screen.screenName.asString(),
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp,
                            color = if(selected) MaterialTheme.colors.primaryVariant else Color.Gray,
                        )
                    }
                }
            )
        }
    }
}