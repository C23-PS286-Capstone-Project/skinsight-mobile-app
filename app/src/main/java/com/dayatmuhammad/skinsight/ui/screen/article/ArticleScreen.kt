package com.dayatmuhammad.skinsight.ui.screen.article

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.greyBackground

@Composable
fun ArticleScreen(
    navHostController: NavHostController
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .navigationBarsPadding()
    ) {
        HeaderImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier
                .statusBarsPadding()
                .padding(top = 12.dp))
            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 34.dp
                    )
                    .clip(
                        CircleShape
                    )
                    .clickable {
                        navHostController.popBackStack()
                    },
                text = "<",
                color = Color.White,
                fontSize = 26.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(140.dp))
            Text(
                modifier = Modifier.padding(horizontal = 34.dp),
                text = "Signs of premature ageing and hacks to prevent it",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier
                    .padding(horizontal = 34.dp),
                shape = RoundedCornerShape(24.dp),
                backgroundColor = greyBackground
            ) {
                Column(
                    modifier = Modifier
                        .padding()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 18.dp),
                        text = "Premature ageing can happen as a result of common lifestyle factors and behaviours, all of which can be corrected or altered. Check out these signs of premature ageing and anti-ageing hacks to prevent it\n" +
                                "As you age, so does your skin where fine lines may begin to form around the eyes and mouth and the proteins collagen and elastin—which are responsible for the structure, strength and elasticity of the skin—begin to weaken but for some, these changes occur faster than others, known as premature ageing. Premature aging can happen as a result of common lifestyle factors and behaviours, all of which can be corrected or altered and the more you know about how to avoid premature ageing, the more control you can take toward maintaining a healthier, younger body and mind.",
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .height(140.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            model = "https://c0.wallpaperflare.com/preview/678/252/27/young-old-child-elderly.jpg",
                            contentDescription = "image article")
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 18.dp),
                        text = "Anti-ageing tips: Signs of premature ageing and hacks to prevent it (Photo by Nathan Anderson on Unsplash)\n" +
                                "\n" +
                                "In an interview with HT Lifestyle, Dr Geetika Mittal Gupta, Cosmetologist and MD at International Aesthetics, revealed how to identify the signs of premature ageing and said, “Fine lines, wrinkles, and grey hair are generally the first things that come to mind when talking about aging. It’s important to remember that your skin is unique and there’s no standard for ageing skin that applies to everyone but if you’re under the age of 35, and you notice any of the following telltale signs of aging skin, you may be ageing prematurely. Premature ageing also includes the formation of sunspots (also called liver spots or age spots), dry or itchy skin, sagging skin, sunken cheeks or temples and hyperpigmentation around the chest.”",
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        fontSize = 12.sp,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 13.sp
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                }
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}


@Composable
fun HeaderImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color.White)
            .drawWithContent {
                val colors = listOf(Color.Transparent, Color.White)
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(colors),
                    blendMode = BlendMode.DstOut
                )
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.article1),
            contentDescription = "Profile Foto",
            contentScale = ContentScale.Crop
        )
    }
}