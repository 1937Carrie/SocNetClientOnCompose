package com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts

@Composable
fun SignUpExtendedScreen(onCancelClick: () -> Unit, onForwardClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.custom_blue))
            .padding(dimensionResource(R.dimen.spacer_smaller))
    ) {
        val userName = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }

        DrawSpaceBetweenTopParentAndPictures()
        DrawPictures()
        DrawSpaceBetweenPicturesAndText()
        DrawText()
        DrawSpaceBetweenTextAndTextFields()
        DrawTextFields(userName, phoneNumber)
        DrawButtons(onCancelClick, onForwardClick)
    }
}

@Composable
private fun DrawPictures() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_account_circle),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(Constants.PERCENT_033)
                .aspectRatio(1f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(Constants.PERCENT_033)
                .align(Alignment.CenterEnd),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_add_photo),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
            )
        }
    }
}

@Composable
private fun DrawSpaceBetweenTopParentAndPictures() {
    Spacer(modifier = Modifier.fillMaxSize(Constants.PERCENT_010))
}

@Composable
private fun DrawSpaceBetweenPicturesAndText() {
    Spacer(modifier = Modifier.fillMaxHeight(Constants.PERCENT_004))
}

@Composable
private fun DrawText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.signupext_text_first),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.signupext_text_first_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
        )
        Text(
            text = stringResource(R.string.signupext_text_second),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.spacer_small)),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.signupext_text_second_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS.fontFamily
        )
    }
}

@Composable
private fun DrawSpaceBetweenTextAndTextFields() {
    Spacer(modifier = Modifier.fillMaxHeight(Constants.PERCENT_0035))
}

@Composable
private fun DrawTextFields(userName: MutableState<String>, phoneNumber: MutableState<String>) {
    DrawTextField(value = userName)
    DrawTextField(value = phoneNumber, true)
}

@Composable
private fun DrawTextField(
    value: MutableState<String>, isPhoneNumber: Boolean = false
) {
    TextField(
        value = value.value,
        onValueChange = {
            value.value = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = if (isPhoneNumber) stringResource(R.string.signupext_mobile_phone) else stringResource(
                    R.string.signupext_username
                ), color = colorResource(R.color.custom_gray_4)
            )
        },
        keyboardOptions = if (isPhoneNumber) KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ) else KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text
        ),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.custom_white),
            backgroundColor = Color.Transparent,
            cursorColor = colorResource(R.color.custom_white),
            errorCursorColor = colorResource(R.color.custom_white),
            focusedIndicatorColor = colorResource(R.color.custom_gray_4),
            unfocusedIndicatorColor = colorResource(R.color.custom_gray_4),
            disabledIndicatorColor = colorResource(R.color.custom_gray_4),
            errorIndicatorColor = colorResource(R.color.custom_gray_4),
        )
    )
}

@Composable
private fun DrawButtons(
    onCancelClick: () -> Unit,
    onForwardClick: () -> Unit,
) {
    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Bottom) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.signupext_text_button_cancel_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
                .background(Color.Transparent)
                .border(
                    dimensionResource(R.dimen.border_width_bigger),
                    colorResource(R.color.custom_white),
                    RoundedCornerShape(
                        dimensionResource(R.dimen.rounded_corner_size)
                    )
                )
                .clickable { onCancelClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.signupext_cancel),
                color = colorResource(R.color.custom_white),
                fontSize = dimensionResource(R.dimen.contactlist_text_cancel_fontsize).value.sp,
                fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
            )
        }
        Box(
            Modifier
                .padding(top = dimensionResource(R.dimen.spacer_smaller))
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.signupext_text_button_forward_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
                .background(colorResource(R.color.custom_orange))
                .clickable {
                    onForwardClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.signupext_forward).uppercase(),
                color = colorResource(R.color.custom_white),
                fontSize = dimensionResource(R.dimen.contactlist_text_forward_fontsize).value.sp,
                fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
                letterSpacing = dimensionResource(R.dimen.signup_text_register_letterspacing).value.sp,
            )
        }
    }
}