package com.dayatmuhammad.skinsight

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dayatmuhammad.skinsight.preference.SharedPreference
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.screen.result.ResultActivity
import com.dayatmuhammad.skinsight.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navControllerMain: NavHostController

    @Inject
    lateinit var preference: SharedPreference

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ResultActivity.REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showMessage("Tidak mendapatkan permission.", this)
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = ResultActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setFullyTransparentStatusBar()
        setContent {
            val isLogin = preference.isLogin()
            val navController = rememberNavController()
            navControllerMain = navController
            if (isLogin){
                SkinsightApp(navController, Screen.Home.route)
            }else{
                SkinsightApp(navController, Screen.OnBoarding.route)
            }

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navControllerMain.handleDeepLink(intent)
    }

    @SuppressWarnings("deprecation")
    private fun setFullyTransparentStatusBar() {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindow(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setWindow(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setWindow(activity: Activity, bits: Int, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}