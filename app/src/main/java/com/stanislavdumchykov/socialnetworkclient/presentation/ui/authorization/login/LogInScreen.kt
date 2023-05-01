package com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.SharedViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Status

@Composable
fun LogInScreen(
    sharedViewModel: SharedViewModel,
    logInViewModel: LogInViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.custom_blue))
            .padding(dimensionResource(R.dimen.spacer_smaller))
    ) {
        val email = rememberSaveable { mutableStateOf("testemail3@asd.ds") }
        val isErrorEmail = rememberSaveable { mutableStateOf(false) }
        val password = rememberSaveable { mutableStateOf("qq11223344") }
        val isErrorPassword = rememberSaveable { mutableStateOf(false) }
        val autologinState = remember { mutableStateOf(true) }

        DrawSpaceBetweenTopParentAndText()
        DrawText()
        DrawSpaceBetweenTextAndTextFields()
        DrawTextFields(email, isErrorEmail, password, isErrorPassword)
        DrawSpaceBetweenTextfieldsAndRememberMe()
        DrawRememberMeCheckBox(autologinState = autologinState)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            DrawButtonLogin(email, password, logInViewModel, sharedViewModel, onLoginClick)
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_smaller)))
            DrawTextBottom(onSignUpClick)
        }
    }
}

@Composable
private fun DrawSpaceBetweenTopParentAndText() {
    Spacer(modifier = Modifier.fillMaxSize(Constants.PERCENT_010))
}

@Composable
private fun DrawText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.login_text_hello),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.signupext_text_first_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
        )
        Text(
            text = stringResource(R.string.login_text_enter_email),
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
private fun DrawTextFields(
    email: MutableState<String>,
    isErrorEmail: MutableState<Boolean>,
    password: MutableState<String>,
    isErrorPassword: MutableState<Boolean>
) {
    DrawTextField(email, isErrorEmail)
    DrawTextField(password, isErrorPassword, true)
}

@Composable
private fun DrawTextField(
    value: MutableState<String>, isValueError: MutableState<Boolean>, isPassword: Boolean = false
) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = if (isPassword) stringResource(R.string.signup_text_password) else stringResource(
                    R.string.signup_text_email
                ), color = colorResource(R.color.custom_gray_4)
            )
        },
        isError = isValueError.value,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions.Default else KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email
        ),
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
    Text(
        text = if (isValueError.value) {
            if (isPassword) stringResource(R.string.signup_text_password_error)
            else stringResource(R.string.signup_text_email_error)
        } else "",
        modifier = Modifier
            .padding(start = dimensionResource(R.dimen.signup_padding_bigger))
            .fillMaxWidth(),
        color = colorResource(R.color.custom_error),
        fontSize = dimensionResource(R.dimen.signup_error_text_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS.fontFamily,
    )
}

@Composable
private fun DrawSpaceBetweenTextfieldsAndRememberMe() {
    Spacer(Modifier.height(dimensionResource(R.dimen.signup_padding_bigger)))
}

@Composable
private fun DrawRememberMeCheckBox(autologinState: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(R.drawable.ic_checkbox_background),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        autologinState.value = !autologinState.value
                    })
                if (autologinState.value) {
                    Image(
                        painter = painterResource(R.drawable.ic_checkbox_checker),
                        contentDescription = "",
                    )
                }
            }
            DrawCheckBoxText()
        }
        Text(
            text = stringResource(R.string.login_text_forgot_password),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.login_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
    }
}

@Composable
private fun DrawCheckBoxText() {
    Text(
        text = stringResource(R.string.signup_text_rememberme),
        modifier = Modifier.padding(start = dimensionResource(R.dimen.signup_padding)),
        color = colorResource(R.color.custom_gray_2),
        fontSize = dimensionResource(R.dimen.signup_text_rememberme_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
    )
}

@Composable
private fun DrawButtonLogin(
    email: MutableState<String>,
    password: MutableState<String>,
    logInViewModel: LogInViewModel,
    sharedViewModel: SharedViewModel,
    onLoginClick: () -> Unit,
) {
    val status = logInViewModel.status.observeAsState()
    if (status.value?.status == Status.SUCCESS) {
        logInViewModel.user.value?.let { sharedViewModel.addUser(it) }
        onLoginClick()
        logInViewModel.clearAllStatuses()

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.signup_registerbutton_height))
            .background(Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .border(
                dimensionResource(R.dimen.myprofile_button_editprofile_border_width),
                colorResource(R.color.custom_orange),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            )
            .clickable {
                logInViewModel.authorizeUser(email.value, password.value)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.login_text_login).uppercase(),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.signup_text_register_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
            letterSpacing = dimensionResource(R.dimen.signup_text_register_letterspacing).value.sp
        )
    }

}

@Composable
private fun DrawTextBottom(onSignUpClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.login_text_dont_have_account).uppercase(),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.login_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Spacer(Modifier.width(dimensionResource(R.dimen.spacer_smaller)))
        Text(
            text = stringResource(R.string.login_text_sign_up).uppercase(),
            modifier = Modifier.clickable { onSignUpClick() },
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.login_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
    }

}