package com.dayatmuhammad.skinsight.ui.screen.confirmdetect

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.ui.screen.result.ResultActivity
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.lightBlue

@Composable
fun ConfirmDetect(
    navHostController: NavHostController,
) {
    val mContext = LocalContext.current
    val hasCameraPermission = remember { mutableStateOf(false) }

    val requestCameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                hasCameraPermission.value = true
                // Permission granted, you can use the camera here
                // Show your camera-related UI or launch camera
            } else {
                // Permission denied, handle the denial case
                // Show an error message or alternative UI
            }
        }

    if (!hasCameraPermission.value) {
        // Request the camera permission
        LaunchedEffect(Unit) {
            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 28.dp)
                    .fillMaxWidth()
                    .height(36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .clickable {
                            navHostController.popBackStack()
                        },
                    contentScale = ContentScale.Crop,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp),
                    text = "Detect Premature Ageing",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    modifier = Modifier.width(220.dp),
                    text = "Check the premature aging status briefly and accurately",
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    modifier = Modifier
                        .height(260.dp),
                    painter = painterResource(id = R.drawable.confirm_detect),
                    contentDescription = "Confirm detect"
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    modifier = Modifier
                        .padding(horizontal = 34.dp)
                        .fillMaxWidth(),
                    onClick = {
                        mContext.startActivity(Intent(mContext, ResultActivity::class.java))
                              },
                    colors = ButtonDefaults.buttonColors(lightBlue),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        text = "Scan Now",
                        textAlign = TextAlign.Center,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    )
}