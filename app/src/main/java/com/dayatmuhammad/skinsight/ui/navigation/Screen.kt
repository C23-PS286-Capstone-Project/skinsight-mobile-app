package com.dayatmuhammad.skinsight.ui.navigation

sealed class Screen(val route: String) {
    object OnBoarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ConfirmDetect : Screen("confirm")
    object Result : Screen("result")
    object Profile : Screen("profile")
    object Article : Screen("article")
    object History : Screen("history")
}