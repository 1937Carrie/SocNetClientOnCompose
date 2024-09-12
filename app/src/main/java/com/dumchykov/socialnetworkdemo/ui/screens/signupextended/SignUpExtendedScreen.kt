package com.dumchykov.socialnetworkdemo.ui.screens.signupextended

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.Pager
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import com.dumchykov.socialnetworkdemo.ui.util.customTextFieldsColors

@Composable
fun SignUpExtendedScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpExtendedViewModel = hiltViewModel(),
) {
    val signUpExtendedState = viewModel.signUpExtendedState.collectAsState().value
    val updateState: (SignUpExtendedState) -> Unit =
        { updatedState -> viewModel.updateState { updatedState } }
    val onForwardClick: () -> Unit = { viewModel.editUser() }

    LaunchedEffect(signUpExtendedState.navigateForward) {
        if (signUpExtendedState.navigateForward.not()) return@LaunchedEffect
        navController.navigate(Pager) {
            popUpTo(Pager) {
                inclusive = true
            }
        }
    }

    SignUpExtendedScreen(
        padding = padding,
        signUpExtendedState = signUpExtendedState,
        updateState = updateState,
        onForwardClick = onForwardClick,
    )
}

@Composable
private fun SignUpExtendedScreen(
    padding: PaddingValues,
    signUpExtendedState: SignUpExtendedState,
    updateState: (SignUpExtendedState) -> Unit = {},
    onForwardClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(Blue)
            .fillMaxSize()
            .padding(
                start = max(padding.calculateStartPadding(LayoutDirection.Ltr), 16.dp),
                top = max(padding.calculateTopPadding(), 16.dp),
                end = max(padding.calculateEndPadding(LayoutDirection.Ltr), 16.dp),
                bottom = max(padding.calculateBottomPadding(), 16.dp)
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Bottom)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        tint = Gray
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(0.15f)
                        .aspectRatio(1f)
                        .align(BiasAbsoluteAlignment(0.66f, 0f)),
                ) {
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        tint = Gray
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.your_profile_data),
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
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = signUpExtendedState.userName,
                onValueChange = {
                    updateState(signUpExtendedState.copy(userName = it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.user_name)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true,
                colors = customTextFieldsColors()
            )
            TextField(
                value = signUpExtendedState.mobilePhone,
                onValueChange = {
                    updateState(signUpExtendedState.copy(mobilePhone = it))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.mobile_phone)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                singleLine = true,
                colors = customTextFieldsColors()
            )
        }
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(Color.Transparent, White, Color.Transparent, White),
                border = BorderStroke(2.dp, White)
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
            Button(
                onClick = onForwardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(Orange, White, Orange, White)
            ) {
                Text(
                    text = stringResource(R.string.forward).uppercase(),
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpExtendedScreenPreview() {
    SignUpExtendedScreen(
        padding = PaddingValues(0.dp),
        signUpExtendedState = SignUpExtendedState(),
    )
}