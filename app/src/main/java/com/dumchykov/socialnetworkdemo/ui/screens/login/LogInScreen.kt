package com.dumchykov.socialnetworkdemo.ui.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.LogIn
import com.dumchykov.socialnetworkdemo.ui.screens.Pager
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import com.dumchykov.socialnetworkdemo.ui.util.customTextFieldsColors
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState

@Composable
fun LogInScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = hiltViewModel(),
) {
    val logInState = viewModel.logInState.collectAsState().value
    val updateState: (LogInState) -> Unit =
        { updatedState -> viewModel.updateState { updatedState } }
    val validateEmail: (String) -> Unit = { email -> viewModel.validateEmail(email) }
    val validatePassword: (String) -> Unit = { password -> viewModel.validatePassword(password) }
    val onLogInClick: () -> Unit = { viewModel.authorize() }
    val onSignUpClick: () -> Unit = { navController.navigate(SignUp) }
    val saveCredentials: () -> Unit = { viewModel.saveCredentials() }

    LaunchedEffect(logInState.autoLogin) {
        if (logInState.autoLogin) {
            onLogInClick()
        }
    }

    LaunchedEffect(logInState.responseState) {
        if (logInState.responseState !is ResponseState.Success<*>) return@LaunchedEffect

        navController.navigate(Pager) {
            popUpTo(LogIn) {
                inclusive = true
            }
        }
    }

    LogInScreen(
        padding = padding,
        logInState = logInState,
        updateState = updateState,
        validateEmail = validateEmail,
        validatePassword = validatePassword,
        onLogInClick = onLogInClick,
        onSignUpClick = onSignUpClick,
        saveCredentials = saveCredentials
    )
}

@Composable
private fun LogInScreen(
    padding: PaddingValues,
    logInState: LogInState,
    updateState: (LogInState) -> Unit,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    saveCredentials: () -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(Blue)
            .fillMaxSize()
            .padding(padding)
    ) {
        if (maxWidth < 500.dp) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Container1(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
                Container2(
                    logInState = logInState,
                    updateState = updateState,
                    validateEmail = validateEmail,
                    validatePassword = validatePassword,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
                Container3(
                    logInState = logInState,
                    updateState = updateState,
                    onLogInClick = onLogInClick,
                    onSignUpClick = onSignUpClick,
                    saveCredentials = saveCredentials,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                )
            }
        } else {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Container1(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    Container2(
                        logInState = logInState,
                        updateState = updateState,
                        validateEmail = validateEmail,
                        validatePassword = validatePassword,
                        modifier = Modifier.weight(2f)
                    )
                }
                Container3(
                    logInState = logInState,
                    updateState = updateState,
                    onLogInClick = onLogInClick,
                    onSignUpClick = onSignUpClick,
                    saveCredentials = saveCredentials,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Container3(
    logInState: LogInState,
    updateState: (LogInState) -> Unit,
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    saveCredentials: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (logInState.email.isNotEmpty() && logInState.emailError.not() && logInState.password.isNotEmpty() && logInState.passwordError.not()) {
                    if (logInState.rememberMe) {
                        saveCredentials()
                    }
                    onLogInClick()
                } else {
                    if (logInState.email.isEmpty()) {
                        updateState(
                            if (logInState.emailIsFocused.not()) {
                                logInState.copy(emailError = true, emailIsFocused = true)
                            } else {
                                logInState.copy(emailError = true)
                            }
                        )
                    }
                    if (logInState.password.isEmpty()) {
                        updateState(
                            if (logInState.passwordIsFocused.not()) {
                                logInState.copy(passwordError = true, passwordIsFocused = true)
                            } else {
                                logInState.copy(passwordError = true)
                            }
                        )
                    }
                }
            },
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            enabled = logInState.isUiStateUpdating.not(),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonColors(
                Color.Transparent,
                Color.Black,
                Color.Transparent,
                Color.Black
            ),
            border = BorderStroke(2.dp, Orange)
        ) {
            if (logInState.isUiStateUpdating) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.authorization).uppercase(),
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS,
                        letterSpacing = 1.5.sp
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .height(22.dp)
                            .aspectRatio(1f),
                        color = Orange
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.login).uppercase(),
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                    letterSpacing = 1.5.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.don_t_have_account),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
            Text(
                text = stringResource(R.string.sign_up),
                modifier = Modifier
                    .clickable(
                        enabled = logInState.isUiStateUpdating.not(),
                        onClick = onSignUpClick
                    ),
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
        }
    }
}

@Composable
private fun Container2(
    logInState: LogInState,
    updateState: (LogInState) -> Unit,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = logInState.email,
            onValueChange = {
                updateState(logInState.copy(email = it))
                validateEmail(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (logInState.emailIsFocused.not()) {
                            updateState(logInState.copy(emailIsFocused = true))

                        }
                    }
                },
            enabled = logInState.isUiStateUpdating.not(),
            placeholder = { Text(text = stringResource(R.string.email)) },
            supportingText = {
                if (logInState.emailIsFocused && logInState.emailError) {
                    Text(text = stringResource(R.string.incorrect_e_mail_address))
                }
            },
            isError = logInState.emailError,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            singleLine = true,
            colors = customTextFieldsColors()
        )
        TextField(
            value = logInState.password,
            onValueChange = {
                updateState(logInState.copy(password = it))
                validatePassword(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (logInState.passwordIsFocused.not()) {
                            updateState(logInState.copy(passwordIsFocused = true))
                        }
                    }
                },
            enabled = logInState.isUiStateUpdating.not(),
            placeholder = { Text(stringResource(R.string.password)) },
            supportingText = {
                if (logInState.passwordIsFocused && logInState.passwordError) {
                    Text(text = stringResource(R.string.password_is_empty))
                }
            },
            isError = logInState.passwordError,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            singleLine = true,
            colors = customTextFieldsColors()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable(
                    enabled = logInState.isUiStateUpdating.not(),
                    onClick = {
                        updateState(logInState.copy(rememberMe = logInState.rememberMe.not()))
                    }),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(if (logInState.rememberMe) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox),
                    contentDescription = stringResource(R.string.checkbox_remember_me),
                    tint = White
                )
                Text(
                    text = stringResource(R.string.remember_me),
                    color = Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
            Text(
                text = stringResource(R.string.forgot_your_password),
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
        }
    }
}

@Composable
private fun Container1(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hello),
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.W600,
            fontFamily = OPENS_SANS,
        )
        Text(
            text = stringResource(R.string.enter_your_email_and_password_below),
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = OPENS_SANS,
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=landscape"
)
@Composable
fun LogInScreenPreview() {
    LogInScreen(
        padding = PaddingValues(0.dp),
        logInState = LogInState(),
        updateState = {},
        validateEmail = {},
        validatePassword = {},
        onLogInClick = {},
        onSignUpClick = {},
        saveCredentials = {}
    )
}
