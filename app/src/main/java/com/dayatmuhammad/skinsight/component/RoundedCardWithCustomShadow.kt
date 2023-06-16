package com.dayatmuhammad.skinsight.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayatmuhammad.skinsight.ui.theme.SkinsightTheme

@Composable
fun RoundedCardWithCustomShadow() {
    val customShadowColor = Color.Red // Set your desired shadow color

    Surface(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        color = Color.White // Set the background color of the card
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Rounded Card with Custom Shadow", fontSize = 20.sp)
        }
    }
}

@Preview (showBackground = true)
@Composable
fun PreviewRoundedCardWithCustomShadow(){
    SkinsightTheme() {
        RoundedCardWithCustomShadow()
    }
}