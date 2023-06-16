package com.dayatmuhammad.skinsight

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.dayatmuhammad.skinsight.ui.navigation.SkinsightNavigation
import com.dayatmuhammad.skinsight.ui.theme.SkinsightTheme

@Composable
fun SkinsightApp(navController: NavHostController, startDestination: String) {
    SkinsightTheme() {
        SkinsightNavigation(
            navController = navController,
            startDestination
        )
    }
}
