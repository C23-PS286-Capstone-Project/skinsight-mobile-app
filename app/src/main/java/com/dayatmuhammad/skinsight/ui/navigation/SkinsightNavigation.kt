package com.dayatmuhammad.skinsight.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dayatmuhammad.skinsight.preference.SharedPreference
import com.dayatmuhammad.skinsight.ui.screen.article.ArticleScreen
import com.dayatmuhammad.skinsight.ui.screen.confirmdetect.ConfirmDetect
import com.dayatmuhammad.skinsight.ui.screen.history.HistoryScreen
import com.dayatmuhammad.skinsight.ui.screen.home.HomeScreen
import com.dayatmuhammad.skinsight.ui.screen.login.LoginScreen
import com.dayatmuhammad.skinsight.ui.screen.onboarding.OnBoardingScreen
import com.dayatmuhammad.skinsight.ui.screen.profile.ProfileScreen
import com.dayatmuhammad.skinsight.ui.screen.register.RegisterScreen

@Composable
fun SkinsightNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.OnBoarding.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.ConfirmDetect.route) {
            ConfirmDetect(navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Screen.Article.route) {
            ArticleScreen(navController)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController)
        }
    }
}