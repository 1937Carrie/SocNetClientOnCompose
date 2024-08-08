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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.MyProfile
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
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
) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.factory(context))
    val signUpState = signUpViewModel.signUpState.collectAsState().value

    LaunchedEffect(signUpState.autoLogin) {
        if (signUpState.autoLogin) {
            navigateNext(navController, signUpState)
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
                    signUpState,
                    signUpViewModel,
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .weight(1f)
                )
                Container3(
                    signUpState,
                    signUpViewModel,
                    navController,
                    Modifier
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
                        signUpViewModel = signUpViewModel,
                        modifier = Modifier.weight(2f)
                    )
                }
                Container3(
                    signUpState = signUpState,
                    signUpViewModel = signUpViewModel,
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

}

@Composable
private fun Container3(
    signUpState: SignUpState,
    signUpViewModel: SignUpViewModel,
    navController: NavHostController,
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
                Image(painterResource(id = R.drawable.ic_google), "icon google")
                Text(
                    text = "Google".uppercase(),
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
            text = "or",
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
                        signUpViewModel.saveCredentials()
                    }
                    navigateNext(navController, signUpState)
                } else {
                    if (signUpState.email.isEmpty()) {
                        signUpViewModel.updateState {
                            if (signUpState.emailIsFocused.not()) {
                                copy(emailError = true, emailIsFocused = true)
                            } else {
                                copy(emailError = true)
                            }
                        }
                    }
                    if (signUpState.password.isEmpty()) {
                        signUpViewModel.updateState {
                            if (signUpState.passwordIsFocused.not()) {
                                copy(passwordError = true, passwordIsFocused = true)
                            } else {
                                copy(passwordError = true)
                            }
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
                text = "Register".uppercase(),
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS,
                letterSpacing = 1.5.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "By clicking Register you agree to our Terms and Conditions",
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
                text = "Already have an account?",
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
            Text(
                text = "Sign in",
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
    signUpViewModel: SignUpViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = signUpState.email,
            onValueChange = {
                signUpViewModel.updateState { copy(email = it) }
                signUpViewModel.validateEmail(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (signUpState.emailIsFocused.not()) signUpViewModel.updateState {
                            copy(emailIsFocused = true)
                        }
                    }
                },
            placeholder = { Text("Email") },
            supportingText = {
                if (signUpState.emailIsFocused && signUpState.emailError) {
                    Text(text = "Incorrect E-Mail address")
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
                signUpViewModel.updateState { copy(password = it) }
                signUpViewModel.validatePassword(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        if (signUpState.passwordIsFocused.not()) signUpViewModel.updateState {
                            copy(
                                passwordIsFocused = true
                            )
                        }
                    }
                },
            placeholder = { Text("Password") },
            supportingText = {
                if (signUpState.passwordIsFocused && signUpState.passwordError) {
                    Text(text = "Password is empty")
                }
            },
            isError = signUpState.passwordError,
            singleLine = true,
            colors = customTextFieldsColors()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(if (signUpState.rememberMe) R.drawable.ic_checkbox_checked else R.drawable.ic_checkbox),
                contentDescription = "checkbox remember me",
                modifier = Modifier.clickable {
                    signUpViewModel.updateState { copy(rememberMe = rememberMe.not()) }
                },
                tint = White
            )
            Text(
                text = "Remember me",
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
            text = "Let's get acquainted",
            color = White,
            fontSize = 24.sp,
            fontWeight = FontWeight.W600,
            fontFamily = OPENS_SANS,
        )
        Text(
            text = "Fill out the profile and go to the application!",
            color = White,
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            fontFamily = OPENS_SANS,
        )
    }
}

private fun navigateNext(
    navController: NavHostController,
    signUpState: SignUpState,
) {
    navController.navigate(MyProfile(signUpState.email)) {
        popUpTo(SignUp) {
            inclusive = true
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=landscape"
)
@Composable
fun SignUpScreenLandscapePreview() {
    SignUpScreen(padding = PaddingValues(0.dp), rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(padding = PaddingValues(0.dp), rememberNavController())
}
