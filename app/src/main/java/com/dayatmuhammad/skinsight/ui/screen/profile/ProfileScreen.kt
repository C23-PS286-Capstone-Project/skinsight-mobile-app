package com.dayatmuhammad.skinsight.ui.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.component.CheckInternetConnection
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.screen.loading.LoadingLottie
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.grey
import com.dayatmuhammad.skinsight.ui.theme.lightBlue

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateProfile.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val textName = rememberSaveable {
        mutableStateOf("")
    }
    val textEmail = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val imageUrl = rememberSaveable {
        mutableStateOf("")
    }
    val showLoading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (CheckInternetConnection(context).value){
            showLoading.value = true
            val token = viewModel.getToken()
            viewModel.getProfile("Bearer $token")
        }else{
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }
    LaunchedEffect(uiState) {
        uiState.let {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    showLoading.value = false
                    textName.value = it.data.data?.name.toString()
                    textEmail.value = it.data.data?.email.toString()
                    imageUrl.value = it.data.data?.picture.toString()
                }
                is Result.Error -> {
                    showLoading.value = false
                }
            }
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
                        .padding(horizontal = 34.dp)
                        .padding(end = 34.dp),
                    text = "My Profile",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
            }
        },
        content = {
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Are you sure?") },
                    text = { Text(text = "Do you want to Logout") },
                    confirmButton = {
                        Text(
                            modifier = Modifier.clickable {
                                showDialog = false
                                viewModel.setIsLogin(false)
                                viewModel.clearToken()
                                navHostController.navigate(Screen.Login.route)
                            },
                            text = "Confirm"
                        )
                    },
                    dismissButton = {
                        Text(
                            modifier = Modifier.clickable {
                                showDialog = false
                            },
                            text = "Cancel"
                        )
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(45.dp))
                    Card(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(111.dp),
                        shape = CircleShape
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = imageUrl.value,
                            contentDescription = "Profile Foto"
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .align(Alignment.CenterHorizontally),
                        text = textName.value,
                        fontSize = 16.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .align(Alignment.CenterHorizontally),
                        text = textEmail.value,
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 13.sp
                    )
                    Spacer(modifier = Modifier.height(34.dp))
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .align(Alignment.Start),
                        text = "Personal Information",
                        fontSize = 16.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 34.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.phone),
                            contentDescription = "Profile Foto"
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "Phone",
                                color = grey,
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "+62 (5169)-639-0421",
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 34.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(24.dp),
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "Profile Foto"
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "Location",
                                color = grey,
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "Malang, Indonesia",
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                navHostController.navigate(Screen.History.route)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)
                                .size(24.dp),
                            imageVector = Icons.Default.History,
                            contentDescription = "History Icon",
                            tint = grey
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "History",
                                color = grey,
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 34.dp),
                                text = "Prediction History",
                                fontSize = 12.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 13.sp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
                if (showLoading.value) {
                    LoadingLottie()
                }
            }

        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 34.dp),
                onClick = {
                    showDialog = true
                },
                colors = ButtonDefaults.buttonColors(lightBlue),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    text = "Log Out",
                    textAlign = TextAlign.Center,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 18.sp
                )
            }
        }
    )
}