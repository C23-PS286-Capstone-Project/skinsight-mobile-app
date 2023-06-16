package com.dayatmuhammad.skinsight.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dayatmuhammad.skinsight.component.CheckInternetConnection
import com.dayatmuhammad.skinsight.component.RoundedTextField
import com.dayatmuhammad.skinsight.data.ErrorResponseLogin
import com.dayatmuhammad.skinsight.data.LoginRequest
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.screen.loading.LoadingLottie
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.grey
import com.dayatmuhammad.skinsight.ui.theme.greySecondary
import com.dayatmuhammad.skinsight.ui.theme.lightBlue
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiStateProfile.collectAsState()

    val textEmail = rememberSaveable {
        mutableStateOf("")
    }
    val textPassword = rememberSaveable {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val enableLoginButton = rememberSaveable {
        mutableStateOf(false)
    }
    val showLoading = remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        uiState.let {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    showLoading.value = false
                    viewModel.setToken(it.data.data?.token.toString())
                    viewModel.setIsLogin(true)
                    navHostController.navigate(Screen.Home.route)
                }
                is Result.Error -> {
                    showLoading.value = false
                    val err = it.errorBody?.string()?.let { it1 -> JSONObject(it1).toString() }
                    val gson = Gson()
                    val jsonObject = gson.fromJson(err, JsonObject::class.java)
                    val errorResponse = gson.fromJson(jsonObject, ErrorResponseLogin::class.java)
                    val messageErr = errorResponse.message
                    Toast.makeText(context, messageErr, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding(),
        content = { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(34.dp)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(
                        modifier = Modifier,
                        text = "Hey, \nLogin Now",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "If you are new /",
                            color = grey,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    navHostController.navigate(Screen.Register.route)
                                },
                            text = " Sign Up",
                            color = Color.Black,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding(),
                        text = textEmail,
                        onTextChanged = {
                            textEmail.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Username"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding(),
                        text = textPassword,
                        onTextChanged = {
                            textPassword.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Password",
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(27.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Forgot Password / ",
                            color = grey,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier,
                            text = "Reset",
                            color = Color.Black,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(38.dp))
                    enableLoginButton.value = textEmail.value.isNotEmpty() && textPassword.value.isNotEmpty()
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enableLoginButton.value,
                        onClick = {
                            coroutineScope.launch {
                                if (CheckInternetConnection(context).value){
                                    showLoading.value = true
                                    viewModel.login(LoginRequest(textEmail.value, textPassword.value))
                                }else{
                                    Toast.makeText(context,"No Internet Connection", Toast.LENGTH_SHORT).show()
                                }

                            }

                        },
                        colors = ButtonDefaults.buttonColors(lightBlue),
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            text = "Login",
                            textAlign = TextAlign.Center,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
                if (showLoading.value) {
                    LoadingLottie()
                }
            }
        }
    )
}