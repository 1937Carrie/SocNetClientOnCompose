package com.stanislavdumchykov.socialnetworkclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts

private const val PERCENT_40 = 0.4f
private const val PERCENT_60 = 0.6f

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawScreen()
        }
    }
}

@Composable
private fun DrawScreen() {
    DrawBackground()
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.signup_padding_bigger))
                .fillMaxWidth()
                .fillMaxHeight(PERCENT_60),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DrawFirstText()
                DrawSecondText()
                Column(
                    modifier = Modifier
                        .padding(vertical = dimensionResource(R.dimen.signup_padding_bigger))
                ) {
                    DrawEmailField()
                    DrawPasswordField()
                }
            }
            Row(
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.signup_padding_bigger))
            ) {
                DrawCheckBoxIcon()
                DrawCheckBoxText()
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.signup_padding_bigger))
                .fillMaxWidth()
                .fillMaxHeight(PERCENT_40),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DrawGoogleButton()
            DrawOrText()
            DrawRegisterButton()
            DrawTermAndConditionsText()
            DrawAlreadyHaveAccountText()
        }
    }
}

@Composable
private fun DrawAlreadyHaveAccountText() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.signup_text_alreadyhaveaccount),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.signup_text_signin_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD
        )
        Text(
            text = stringResource(R.string.signup_text_signin),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.signup_text_padding)),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.signup_text_signin_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD
        )
    }

}

@Composable
private fun DrawTermAndConditionsText() {
    Text(
        text = stringResource(R.string.signup_text_termsandcondition),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_termandconditions_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPENSANS,
    )
}

@Composable
private fun DrawRegisterButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.signup_registerbutton_height))
            .background(Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable { }
            .border(
                dimensionResource(R.dimen.myprofile_button_editprofile_border_width),
                colorResource(R.color.custom_orange),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.signup_text_register).uppercase(),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.signup_text_register_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
            letterSpacing = dimensionResource(R.dimen.signup_text_register_letterspacing).value.sp
        )
    }
}

@Composable
private fun DrawOrText() {
    Text(
        text = stringResource(R.string.signup_text_or),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_or_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
    )
}

@Composable
private fun DrawGoogleButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.signup_googlebutton_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .background(colorResource(R.color.white))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.signup_text_google).uppercase(),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.signup_padding_bigger))
            )
        }
    }
}

@Composable
fun DrawCheckBoxText() {
    Text(
        text = stringResource(R.string.signup_text_rememberme),
        modifier = Modifier.padding(start = dimensionResource(R.dimen.signup_padding)),
        color = colorResource(R.color.custom_gray_2),
        fontSize = dimensionResource(R.dimen.signup_text_rememberme_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD
    )
}

@Composable
fun DrawCheckBoxIcon() {
    var checkboxState by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_checkbox_background),
            contentDescription = "",
            modifier = Modifier
                .clickable { checkboxState = !checkboxState }
        )
        if (checkboxState) {
            Image(
                painter = painterResource(R.drawable.ic_checkbox_checker),
                contentDescription = "",
            )
        }

    }
}

@Composable
private fun DrawPasswordField() {
    var password by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { password = it },
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = stringResource(R.string.signup_text_password),
                color = colorResource(R.color.custom_gray_4)
            )
        },
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
    if (isError) {
        Text(
            text = stringResource(R.string.signup_text_password_error),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.signup_padding_bigger))
        )
    }

}

@Composable
private fun DrawEmailField() {
    var email by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = email,
        onValueChange = { email = it },
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = stringResource(R.string.signup_text_email),
                color = colorResource(R.color.custom_gray_4)
            )
        },
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
    if (isError) {
        Text(
            text = stringResource(R.string.signup_text_email_error),
            modifier = Modifier.padding(start = dimensionResource(R.dimen.signup_padding_bigger))
        )
    }
}

@Composable
private fun DrawSecondText() {
    Text(
        text = stringResource(R.string.signup_text_fillouttheprofile),
        modifier = Modifier
            .padding(vertical = dimensionResource(R.dimen.signup_padding)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_fillouttheprofile_size).value.sp,
        fontFamily = Fonts.FONT_OPENSANS,
    )
}

@Composable
private fun DrawFirstText() {
    Text(
        text = stringResource(R.string.signup_text_letgetacquainted),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_letgetacquainted_size).value.sp,
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
    )
}

@Composable
private fun DrawBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.custom_blue))
    )
}
