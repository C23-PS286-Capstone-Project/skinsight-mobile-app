package com.dayatmuhammad.skinsight.ui.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.grey
import com.dayatmuhammad.skinsight.ui.theme.lightBlue

@Composable
fun OnBoardingScreen(
    navHostController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 34.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboarding),
                    contentDescription = "Onboarding Image",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(450.dp)
                )
                Text(
                    text = "Get Started",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    color = grey,
                    fontSize = 14.sp
                )
                Text(
                    text = "Millions of people care about premature aging.",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    fontSize = 32.sp,
                    lineHeight = 30.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.padding(7.dp),
                        onClick = { navHostController.navigate(Screen.Login.route) },
                        colors = ButtonDefaults.buttonColors(lightBlue)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            text = "Next",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    )
}