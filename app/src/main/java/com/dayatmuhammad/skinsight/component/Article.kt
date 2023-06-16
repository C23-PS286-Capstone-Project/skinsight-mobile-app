package com.dayatmuhammad.skinsight.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.SkinsightTheme

@Composable
fun Article(
    modifier: Modifier = Modifier,
    images: String = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg",
    title: String = "Title",
    description: String = "Description"
) {
    Card(
        modifier = modifier
            .height(195.dp)
            .width(237.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .weight(2f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(images)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(horizontal = 15.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 12.sp
                )
                Text(
                    text = description,
                    fontSize = 10.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 10.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    SkinsightTheme {
        Article()
    }
}