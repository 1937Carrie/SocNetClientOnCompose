package com.stanislavdumchykov.socialnetworkclient.presentation.ui.signup

import android.content.res.Configuration
import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.data.StorageRepositoryImpl
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val PASSWORD_MIN_LENGTH = 8
private const val PASSWORD_PATTERN = "\\d+|\\w+"

@Composable
fun SignUpScreen(onRegisterClick: (String) -> Unit, onSignInClick: () -> Unit) {
    val email = rememberSaveable { mutableStateOf("") }
    val isErrorEmail = rememberSaveable { mutableStateOf(false) }
    val password = rememberSaveable { mutableStateOf("") }
    val isErrorPassword = rememberSaveable { mutableStateOf(false) }
    val autologinState = remember { mutableStateOf(true) }
    val currentConfiguration by remember { mutableStateOf(LocalConfiguration) }
    val currentContext = LocalContext.current
    val store = StorageRepositoryImpl(currentContext)

    val emailValue by store.getEmailToken.collectAsState(initial = "")
    val passwordValue by store.getPasswordToken.collectAsState(initial = "")

    if (emailValue.isNotEmpty() && passwordValue.isNotEmpty()) {
        email.value = emailValue
        password.value = passwordValue

        LaunchedEffect(Unit) {
            onRegisterClick(emailValue)
        }
    }

    if (currentConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        DrawSignUpPortrait(
            onRegisterClick,
            onSignInClick,
            email,
            isErrorEmail,
            password,
            isErrorPassword,
            autologinState,
            store
        )
    } else {
        DrawSignUpLandScape(
            onRegisterClick,
            onSignInClick,
            email,
            isErrorEmail,
            password,
            isErrorPassword,
            autologinState,
            store
        )
    }
}

@Composable
private fun DrawSignUpPortrait(
    onRegisterClick: (String) -> Unit,
    onSignInClick: () -> Unit,
    email: MutableState<String>,
    isErrorEmail: MutableState<Boolean>,
    password: MutableState<String>,
    isErrorPassword: MutableState<Boolean>,
    autologinState: MutableState<Boolean>,
    store: StorageRepositoryImpl,
) {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.custom_blue))
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.signup_padding_bigger)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Constants.PERCENT_060),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DrawFirstText()
            DrawSecondText()
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_normal)))
            DrawTextField(email, isErrorEmail)
            DrawTextField(password, isErrorPassword, true)
            Spacer(Modifier.height(dimensionResource(R.dimen.signup_padding_bigger)))
            DrawRememberMeCheckBox(autologinState = autologinState)
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DrawGoogleButton()
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
            DrawOrText()
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
            DrawRegisterButton(
                email,
                isErrorEmail,
                password,
                isErrorPassword,
                autologinState,
                store,
                onRegisterClick
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_normal)))
            DrawTermAndConditionsText()
            Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
            DrawAlreadyHaveAccountText(onSignInClick)
        }
    }
}

@Composable
private fun DrawSignUpLandScape(
    onRegisterClick: (String) -> Unit,
    onSignInClick: () -> Unit,
    email: MutableState<String>,
    isErrorEmail: MutableState<Boolean>,
    password: MutableState<String>,
    isErrorPassword: MutableState<Boolean>,
    autologinState: MutableState<Boolean>,
    store: StorageRepositoryImpl
) {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.custom_blue))
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.signup_padding_bigger))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawFirstText()
        DrawSecondText()
        Spacer(Modifier.height(dimensionResource(R.dimen.signup_padding_bigger)))
        DrawTextField(email, isErrorEmail)
        DrawTextField(password, isErrorPassword, true)
        Spacer(Modifier.height(dimensionResource(R.dimen.signup_padding_bigger)))
        DrawRememberMeCheckBox(autologinState)
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_normal)))
        DrawGoogleButton()
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
        DrawOrText()
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
        DrawRegisterButton(
            email, isErrorEmail, password, isErrorPassword, autologinState, store, onRegisterClick
        )
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_normal)))
        DrawTermAndConditionsText()
        Spacer(Modifier.height(dimensionResource(R.dimen.spacer_small)))
        DrawAlreadyHaveAccountText(onSignInClick)
    }
}

@Composable
private fun DrawRememberMeCheckBox(autologinState: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth()
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
private fun DrawRegisterButton(
    email: MutableState<String>,
    isErrorEmail: MutableState<Boolean>,
    password: MutableState<String>,
    isErrorPassword: MutableState<Boolean>,
    autologinState: MutableState<Boolean>,
    store: StorageRepositoryImpl,
    onRegisterClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.signup_registerbutton_height))
            .background(Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable {
                isErrorEmail.value = !Patterns.EMAIL_ADDRESS
                    .matcher(email.value)
                    .matches()
                isErrorPassword.value =
                    !(password.value.length >= PASSWORD_MIN_LENGTH && password.value.contains(
                        Regex(PASSWORD_PATTERN)
                    ))

                if (autologinState.value) {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveString(Constants.PREFERENCES_EMAIL, email.value)
                        store.saveString(Constants.PREFERENCES_PASSWORD, password.value)
                    }
                }

                if (!(isErrorEmail.value && isErrorPassword.value)) {
                    onRegisterClick(email.value)
                }
            }
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
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
            letterSpacing = dimensionResource(R.dimen.signup_text_register_letterspacing).value.sp
        )
    }
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
private fun DrawAlreadyHaveAccountText(onSignInClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.signup_text_alreadyhaveaccount),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.signup_text_signin_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
        )
        Spacer(Modifier.width(dimensionResource(R.dimen.signup_text_padding)))
        Text(
            text = stringResource(R.string.signup_text_signin),
            modifier = Modifier
                .clickable { onSignInClick() },
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.signup_text_signin_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
        )
    }

}

@Composable
private fun DrawTermAndConditionsText() {
    Text(
        text = stringResource(R.string.signup_text_termsandcondition),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_termandconditions_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS.fontFamily,
    )
}

@Composable
private fun DrawOrText() {
    Text(
        text = stringResource(R.string.signup_text_or),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_or_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
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
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google), contentDescription = ""
            )
            Text(
                text = stringResource(R.string.signup_text_google).uppercase(),
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.signup_padding_bigger))
            )
        }
    }
}

@Composable
private fun DrawSecondText() {
    Text(
        text = stringResource(R.string.signup_text_fillouttheprofile),
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.signup_padding)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_fillouttheprofile_size).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS.fontFamily,
    )
}

@Composable
private fun DrawFirstText() {
    Text(
        text = stringResource(R.string.signup_text_letgetacquainted),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.signup_text_letgetacquainted_size).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
    )
}