package com.jobinterviewapp.presentation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val screen: Screen,
    @DrawableRes val iconFilledId: Int,
    @DrawableRes val iconOutlinedId: Int,
    val badgeCount: Int = 0
)