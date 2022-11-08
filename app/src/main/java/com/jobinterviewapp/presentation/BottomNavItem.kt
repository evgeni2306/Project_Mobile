package com.jobinterviewapp.presentation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val screen: Screen,
    @DrawableRes val iconId: Int,
    val badgeCount: Int = 0
)