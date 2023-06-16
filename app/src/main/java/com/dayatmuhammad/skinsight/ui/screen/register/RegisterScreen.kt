package com.dayatmuhammad.skinsight.ui.screen.register

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
import com.dayatmuhammad.skinsight.data.RegisterRequest
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.screen.loading.LoadingLottie
import com.dayatmuhammad.skinsight.ui.screen.login.LoginViewModel
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.grey
import com.dayatmuhammad.skinsight.ui.theme.greySecondary
import com.dayatmuhammad.skinsight.ui.theme.lightBlue
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val textName = rememberSaveable {
        mutableStateOf("")
    }
    val username = rememberSaveable {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()
    val textEmail = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val textPassword = rememberSaveable {
        mutableStateOf("")
    }
    val enableRegisterButton = rememberSaveable {
        mutableStateOf(false)
    }
    val showLoading = remember { mutableStateOf(false) }
    val uiState by viewModel.uiStateProfile.collectAsState()
    LaunchedEffect(uiState) {
        uiState.let {
            when (it) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    showLoading.value = false
                    navHostController.navigate(Screen.Login.route)
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
            ){
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(34.dp)
                        .verticalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    Text(
                        modifier = Modifier,
                        text = "Create New \nAccount",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        lineHeight = 36.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Create new account to start care about premature aging",
                            color = grey,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier,
                            text = "Already have account /",
                            color = grey,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    navHostController.popBackStack()
                                },
                            text = " Login",
                            color = Color.Black,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(22.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = textName,
                        onTextChanged = {
                            textName.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Full Name"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = username,
                        onTextChanged = {
                            username.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Username"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = textEmail,
                        onTextChanged = {
                            textEmail.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Email"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    RoundedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = textPassword,
                        onTextChanged = {
                            textPassword.value = it
                        },
                        backgroundColor = greySecondary,
                        cornerRadius = 18.dp,
                        placeholder = "Password",
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(38.dp))
                    enableRegisterButton.value = textEmail.value.isNotEmpty() && textName.value.isNotEmpty() && textPassword.value.isNotEmpty() && username.value.isNotEmpty()
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enableRegisterButton.value,
                        onClick = {
                            coroutineScope.launch {
                                showLoading.value = true
                                if (CheckInternetConnection(context).value){
                                    viewModel.register(
                                        RegisterRequest(
                                            birthday = "12 Des 2000",
                                            address = "Malang",
                                            birthplace = "Sumedang",
                                            gender = "male",
                                            name = textName.value,
                                            email = textEmail.value,
                                            password = textPassword.value,
                                            username = username.value
                                        )
                                    )
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
                            text = "Register",
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