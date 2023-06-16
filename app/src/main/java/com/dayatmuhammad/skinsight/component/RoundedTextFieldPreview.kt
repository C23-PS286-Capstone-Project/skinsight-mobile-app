package com.dayatmuhammad.skinsight.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun RoundedTextFieldPreview() {
    RoundedTextField(
        modifier = Modifier,
        text = rememberSaveable { mutableStateOf("Example") },
        onTextChanged = {},
        backgroundColor = Color.LightGray,
        cornerRadius = 8.dp,
        placeholder = "Placeholder Text",
    )
}

@Composable
fun RoundedTextField(
    modifier: Modifier,
    text: MutableState<String> = rememberSaveable { mutableStateOf("") },
    onTextChanged: (String) -> Unit,
    backgroundColor: Color,
    cornerRadius: Dp,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        if (text.value.isEmpty()) {
            Text(
                text = placeholder,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(vertical = 16.dp),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            )
        }

        BasicTextField(
            value = text.value,
            interactionSource = interactionSource,
            onValueChange = { onTextChanged.invoke(it) },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .padding(vertical = 16.dp)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
            visualTransformation = when {
                isPassword -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            )
        )
    }
}




