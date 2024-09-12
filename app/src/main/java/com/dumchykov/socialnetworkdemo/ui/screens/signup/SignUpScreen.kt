package com.dumchykov.socialnetworkdemo.ui.screens.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.SignUpExtended
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import com.dumchykov.socialnetworkdemo.ui.util.customTextFieldsColors

@Composable
fun SignUpScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val signUpState = viewModel.signUpState.collectAsState().value
    val updateState: (SignUpState) -> Unit = { viewModel.updateState { it } }
    val validateEmail: (String) -> Unit = { email -> viewModel.validateEmail(email) }
    val validatePassword: (String) -> Unit = { password -> viewModel.validatePassword(password) }
    val saveCredentials = { viewModel.saveCredentials() }
    val navigateToSignUpExtended: () -> Unit = { navController.navigate(SignUpExtended) }
    val navigateUp: () -> Unit = { navController.navigateUp() }

    SignUpScreen(
        padding = padding,
        signUpState = signUpState,
        updateState = updateState,
        validateEmail = validateEmail,
        validatePassword = validatePassword,
        saveCredentials = saveCredentials,
        navigateToSignUpExtended = navigateToSignUpExtended,
        navigateUp = navigateUp
    )
}

@Composable
private fun SignUpScreen(
    padding: PaddingValues,
    signUpState: SignUpState,
    updateState: (SignUpState) -> Unit,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    saveCredentials: () -> Unit,
    navigateToSignUpExtended: () -> Unit,
    navigateUp: () -> Unit,
) {
    LaunchedEffect(signUpState.autoLogin) {
        if (signUpState.autoLogin) {
            navigateToSignUpExtended()
        }
    }

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
                    signUpState = signUpState,
                    updateState = updateState,
                    validateEmail = validateEmail,
                    validatePassword = validatePassword,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
                Container3(
                    signUpState = signUpState,
                    onSignInClick = navigateUp,
                    updateState = updateState,
                    saveCredentials = saveCredentials,
                    navigateToSignUpExtended = navigateToSignUpExtended,
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
                        signUpState = signUpState,
                        updateState = updateState,
                        validateEmail = validateEmail,
                        validatePassword = validatePassword,
                        modifier = Modifier.weight(2f)
                    )
                }
                Container3(
                    signUpState = signUpState,
                    onSignInClick = navigateUp,
                    updateState = updateState,
                    saveCredentials = saveCredentials,
                    navigateToSignUpExtended = navigateToSignUpExtended,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Container3(
    signUpState: SignUpState,
    onSignInClick: () -> Unit,
    updateState: (SignUpState) -> Unit,
    saveCredentials: () -> Unit,
    navigateToSignUpExtended: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonColors(Color.White, Color.White, Color.White, Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp,
                    Alignment.CenterHorizontally
                )
            ) {
                Image(
                    painterResource(id = R.drawable.ic_google),
                    contentDescription = stringResource(R.string.google)
                )
                Text(
                    text = stringResource(R.string.google).uppercase(),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                    letterSpacing = 1.5.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.or),
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.W600,
            fontFamily = OPENS_SANS
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (signUpState.email.isNotEmpty() && signUpState.emailError.not() && signUpState.password.isNotEmpty() && signUpState.passwordError.not()) {
                    if (signUpState.rememberMe) {
                        saveCredentials()
                    }
                    navigateToSignUpExtended()
                } else {
                    if (signUpState.email.isEmpty()) {
                        if (signUpState.emailIsFocused.not()) {
                            updateState(signUpState.copy(emailError = true, emailIsFocused = true))
                        } else {
                            updateState(signUpState.copy(emailError = true))
                        }
                    }
                    if (signUpState.password.isEmpty()) {
                        if (signUpState.passwordIsFocused.not()) {
                            updateState(
                                signUpState.copy(
                                    passwordError = true,
                                    passwordIsFocused = true
                                )
                            )
                        } else {
                            updateState(signUpState.copy(passwordError = true))
                        }
                    }
                }
            },
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonColors(
                Color.Transparent,
                Color.Black,
                Color.Transparent,
                Color.Black
            ),
            border = BorderStroke(2.dp, Orange)
        ) {
            Text(
                text = stringResource(R.string.register).uppercase(),
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS,
                letterSpacing = 1.5.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.by_clicking_register_you_agree_to_our_terms_and_conditions),
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = OPENS_SANS,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.already_have_an_account),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
            Text(
                text = stringResource(R.string.sign_in),
                modifier = Modifier
                    .clickable(onClick = onSignInClick),
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
    signUpState: SignUpState,
    updateState: (SignUpState) -> Unit,
    validateEmail: (String) -> Unit,
    validatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = signUpState.email,
            onValueChange = {
                updateState(signUpState.copy(email = it))
                validateEmail(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (signUpState.emailIsFocused.not()) {
                            updateState(signUpState.copy(emailIsFocused = true))
                        }
                    }
                },
            placeholder = { Text(text = stringResource(R.string.email)) },
            supportingText = {
                if (signUpState.emailIsFocused && signUpState.emailError) {
                    Text(text = stringResource(R.string.incorrect_e_mail_address))
                }
            },
            isError = signUpState.emailError,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            singleLine = true,
            colors = customTextFieldsColors()
        )
        TextField(
            value = signUpState.password,
            onValueChange = {
                updateState(signUpState.copy(password = it))
                validatePassword(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (signUpState.passwordIsFocused.not()) {
                            updateState(signUpState.copy(passwordIsFocused = true))
                        }
                    }
                },
            placeholder = { Text(text = stringResource(R.string.password)) },
            supportingText = {
                if (signUpState.passwordIsFocused && signUpState.passwordError) {
                    Text(text = stringResource(R.string.password_is_empty))
                }
            },
            isError = signUpState.passwordError,
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = customTextFieldsColors()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(if (signUpState.rememberMe) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox),
                contentDescription = stringResource(R.string.checkbox_remember_me),
                modifier = Modifier.clickable {
                    updateState(signUpState.copy(rememberMe = signUpState.rememberMe.not()))
                },
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
            text = stringResource(R.string.let_s_get_acquainted),
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.W600,
            fontFamily = OPENS_SANS,
        )
        Text(
            text = stringResource(R.string.fill_out_the_profile_and_go_to_the_application),
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = OPENS_SANS,
        )
    }
}

@Composable
private fun SignUpScreen() {
    val context = LocalContext.current
    val dataStoreProvider = DataStoreProvider(context)

    SignUpScreen(
        padding = PaddingValues(0.dp),
        navController = rememberNavController(),
        viewModel = SignUpViewModel(dataStoreProvider = dataStoreProvider)
    )
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=landscape"
)
@Composable
fun SignUpScreenLandscapePreview() {
//    SignUpScreen(padding = PaddingValues(0.dp), rememberNavController())
    SignUpScreen()
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
//    SignUpScreen(padding = PaddingValues(0.dp), rememberNavController())
    SignUpScreen()
}
