package com.dayatmuhammad.skinsight.ui.screen.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.component.Article
import com.dayatmuhammad.skinsight.component.AutoSlidingCarousel
import com.dayatmuhammad.skinsight.component.CheckInternetConnection
import com.dayatmuhammad.skinsight.data.DummyData
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.ui.navigation.Screen
import com.dayatmuhammad.skinsight.ui.screen.loading.LoadingLottie
import com.dayatmuhammad.skinsight.ui.screen.profile.ProfileViewModel
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.grey
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateProfile.collectAsState()
    val imageSliderData = DummyData.getDummyListImage()
    val articleData = DummyData.getDummyListArticle()
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val showLoading = remember { mutableStateOf(false) }
    val textName = rememberSaveable {
        mutableStateOf("")
    }

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
                }
                is Result.Error -> {
                    showLoading.value = false
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 34.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.foto_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .clickable {
                            navHostController.navigate(Screen.Profile.route)
                        }
                        .size(48.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop,
                )
                Image(
                    painter = painterResource(id = R.drawable.moon_symbol),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                        .size(32.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .padding(horizontal = 34.dp),
                        text = "Hello,",
                        fontSize = 24.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 8.sp
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 34.dp),
                        text = textName.value,
                        fontSize = 24.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 8.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .height(183.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        val gradientBrush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 500f
                        )
                        AutoSlidingCarousel(
                            itemsCount = imageSliderData.size,
                            itemContent = { index ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            uriHandler.openUri(imageSliderData[index].link)
                                        }
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(imageSliderData[index].image)
                                            .build(),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.height(200.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .fillMaxWidth()
                                            .height(100.dp)
                                            .background(brush = gradientBrush)
                                    )
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(bottom = 4.dp)
                                            .padding(horizontal = 8.dp)
                                            .fillMaxWidth(),
                                        text = imageSliderData[index].description,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                        fontFamily = Poppins,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 34.dp),
                        text = "Featured",
                        fontSize = 14.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 30.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .padding(horizontal = 34.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                navHostController.navigate(Screen.ConfirmDetect.route)
                            },
                        elevation = 8.dp,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Column(
                                modifier = Modifier.padding()
                            ) {
                                Text(
                                    text = "Detect Premature Ageing",
                                    fontSize = 14.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 30.sp
                                )
                                Text(
                                    text = "Check the premature aging status briefly and accurately",
                                    fontSize = 11.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 13.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 34.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recommended article",
                            fontSize = 14.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30.sp
                        )
                        Row(
                            modifier = Modifier
                                .padding(start = 7.dp)
                                .wrapContentWidth()
                        ) {
                            Text(
                                text = "See More",
                                color = grey,
                                fontSize = 14.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 30.sp
                            )
                            Image(
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = R.drawable.more_than),
                                contentDescription = "See More"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Spacer(modifier = Modifier.width(34.dp))
                        }
                        items(articleData) { item ->
                            Article(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clickable {
                                        navHostController.navigate(Screen.Article.route)
                                    },
                                images = item.image,
                                title = item.title,
                                description = item.description
                            )
                        }
                    }
                }
                if (showLoading.value) {
                    LoadingLottie()
                }
            }
        },
    )
}