package com.dayatmuhammad.skinsight.ui.screen.history

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.component.CheckInternetConnection
import com.dayatmuhammad.skinsight.data.DataItem
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.ui.screen.loading.LoadingLottie
import com.dayatmuhammad.skinsight.ui.theme.Poppins

@Composable
fun HistoryScreen(
    navHostController: NavHostController,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateProfile.collectAsState()
    val listData = remember { mutableStateListOf<DataItem?>() }
    val showLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit){
        if (CheckInternetConnection(context).value){
            showLoading.value = true
            val token = viewModel.getToken()
            viewModel.getHistory("Bearer $token")
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
                    for (item in it.data.data!!){
                        listData.add(item)
                    }
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
                        .padding(end = 24.dp),
                    text = "History",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                CardListHistory(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(it)
                        .fillMaxSize(),
                    listData.toList()
                )
                if (showLoading.value) {
                    LoadingLottie()
                }
            }
        }
    )
}