package com.dayatmuhammad.skinsight.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.ui.theme.Poppins
import com.dayatmuhammad.skinsight.ui.theme.greySecondary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrimaryEditText(
    modifier: Modifier = Modifier,
    callOnClick: @Composable (() -> Unit)? = null,
    label: String = "",
    onTextChanged: (String) -> Unit = { _ -> },
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnable: Boolean = true,
    isEnableLabel: Boolean = true,
    text: MutableState<String> = rememberSaveable { mutableStateOf("") },
    icon: @Composable (() -> Unit)? = null,
    trailingOnFocus: Boolean = false,
    maxLength: Int? = null,
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isClicked by interactionSource.collectIsPressedAsState()
    val readOnly = callOnClick != null
    val placeHolderText = if (isFocused) "" else label

    if (isClicked && callOnClick != null) {
        callOnClick.invoke()
    }

    BasicTextField(
        modifier = modifier
            .background(Color.Red)
            .border(
                1.dp, when {
                    isFocused -> colorResource(id = R.color.ColorPrimary)
                    else -> colorResource(id = R.color.ColorSecondary)
                }, RoundedCornerShape(24.dp)
            )
            .focusRequester(focusRequester),
        enabled = isEnable,
        readOnly = readOnly,
        value = text.value,
        onValueChange = {
            if (maxLength == null)
                onTextChanged.invoke(it)
            else {
                if (it.length <= maxLength) onTextChanged.invoke(it)
            }
        },
        textStyle = TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = Poppins,
            fontSize = 16.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        interactionSource = interactionSource
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = text.value,
            innerTextField = it,
            enabled = isEnable,
            singleLine = true,
            label = {
                Text(label)
            },
            placeholder = {
                if (isEnableLabel)
                    Text(placeHolderText)
            },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Red,
                textColor = colorResource(id = R.color.PrimaryTextColor),
//                placeholderColor = colorResource(id = R.color.SecondaryTextColor),
//                focusedLabelColor = colorResource(id = R.color.ColorPrimary),
//                disabledTextColor = colorResource(id = R.color.PrimaryTextColor),
//                disabledLabelColor = colorResource(id = R.color.SecondaryTextColor),
//                disabledPlaceholderColor = colorResource(id = R.color.SecondaryTextColor),
//                trailingIconColor = colorResource(id = R.color.ColorIconField),
//                disabledTrailingIconColor = colorResource(id = R.color.ColorIconField)
            ),
            trailingIcon = if (trailingOnFocus) {
                if (isFocused && text.value.isNotEmpty()) icon else null
            } else icon
        )
    }
}