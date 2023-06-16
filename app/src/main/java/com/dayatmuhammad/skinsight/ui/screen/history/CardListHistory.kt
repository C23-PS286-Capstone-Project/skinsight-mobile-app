package com.dayatmuhammad.skinsight.ui.screen.history

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dayatmuhammad.skinsight.data.DataItem
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import java.text.SimpleDateFormat
import java.util.*

@Preview()
@Composable
fun CardListHistory(
    modifier: Modifier = Modifier,
    list: List<DataItem?> = listOf()
) {
    val sortedList = list.sortedByDescending { it?.date }
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    LazyColumn(
        modifier = modifier
    ) {
        items(items = sortedList) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(90.dp)
                                .height(150.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillParentMaxSize(),
                                model = item?.image,
                                contentDescription = "Image",
                                contentScale = ContentScale.None
                            )
                        }

                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        item?.date?.let {
                            inputFormat.parse(it)?.let { date ->
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 14.dp, top = 4.dp),
                                    textAlign = TextAlign.End,
                                    text = date.toString(),
                                    fontFamily = Poppins,
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Normal
                                )
                            }
                        }
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Prediction Result",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Score : ${item?.prediction_score}",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Age : ${item?.prediction_age}",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal
                        )
                        Text(
                            textAlign = TextAlign.Start,
                            text = "Result : ${item?.prediction_result}",
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal
                        )
                    }
                }
            }
        }
    }
}