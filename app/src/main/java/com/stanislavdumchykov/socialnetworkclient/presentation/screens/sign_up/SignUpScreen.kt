package com.stanislavdumchykov.socialnetworkclient.presentation.screens.sign_up

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.data.DataStorage
import com.stanislavdumchykov.socialnetworkclient.presentation.MyProfileActivity
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Blue
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Gray
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Gray2
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Orange
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.SocialNetworkClientTheme
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.White
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.fontOpenSans
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.util.getTextFieldCustomColors
import com.stanislavdumchykov.socialnetworkclient.util.KEY_EMAIL
import com.stanislavdumchykov.socialnetworkclient.util.KEY_PASSWORD
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val checkBoxState = rememberSaveable { mutableStateOf(true) }
    val dataStorage = DataStorage(LocalContext.current)
    val onRegisterPressed = rememberSaveable { mutableStateOf(false) }
    val canGoNext =
        checkEmailValueValidity(emailState.value) && checkPasswordValueValidity(passwordState.value)
    val context = LocalContext.current


    val cachedEmail by produceState("") {
        dataStorage.readString(KEY_EMAIL).collect { value = it }
    }
    val cachedPassword by produceState("") {
        dataStorage.readString(KEY_PASSWORD).collect { value = it }
    }
    LaunchedEffect(cachedEmail, cachedPassword) {
        if (cachedEmail.isNotEmpty() && cachedPassword.isNotEmpty()) {
            emailState.value = cachedEmail
            passwordState.value = cachedPassword
            val startActivityIntent = Intent(context, MyProfileActivity::class.java).apply {
                putExtra(KEY_EMAIL, emailState.value)
            }
            context.startActivity(startActivityIntent)
            context.getActivity()?.finish()
        }
    }

    Column(
        modifier = modifier
            .background(Blue)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TopBlock(
            emailState.value,
            { emailState.value = it },
            passwordState.value,
            { passwordState.value = it },
            checkBoxState.value,
            { onRegisterPressed.value = it },
            onRegisterPressed.value,
            Modifier.weight(4f)
        )
        BottomBlock(
            onRegisterPressed.value,
            { onRegisterPressed.value = it },
            canGoNext,
            checkBoxState.value,
            dataStorage,
            emailState.value,
            passwordState.value,
            Modifier.weight(3f)
        )
    }
}

@Composable
private fun BottomBlock(
    isRegisterPressed: Boolean,
    onRegisterPressed: (Boolean) -> Unit,
    canGoNext: Boolean,
    isCheckBoxChecked: Boolean,
    dataStorage: DataStorage,
    email: String,
    password: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Buttons(
                isRegisterPressed,
                onRegisterPressed,
                canGoNext,
                isCheckBoxChecked,
                dataStorage,
                email,
                password,
            )
            TextUnderButtons()
        }
    }
}

@Composable
private fun TextUnderButtons() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextTermsConditionsAgree()
        SignInBlock()
    }
}

@Composable
private fun SignInBlock() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.register_already_have_an_account),
            color = Gray,
            fontSize = 14.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = stringResource(R.string.register_sign_in),
            color = White,
            fontSize = 14.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun TextTermsConditionsAgree() {
    Text(
        text = stringResource(R.string.register_by_clicking_register_you_agree_to_our_terms_and_conditions),
        color = Gray,
        fontSize = 12.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun Buttons(
    isRegisterPressed: Boolean,
    onRegisterPressed: (Boolean) -> Unit,
    canGoNext: Boolean,
    isCheckBoxChecked: Boolean,
    dataStorage: DataStorage,
    email: String,
    password: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonGoogle()
        TextBetweenButtons()
        ButtonRegister(
            isRegisterPressed,
            onRegisterPressed,
            canGoNext,
            isCheckBoxChecked,
            dataStorage,
            email,
            password,
        )
    }
}

@Composable
private fun ButtonRegister(
    isRegisterPressed: Boolean,
    onRegisterPressed: (Boolean) -> Unit,
    canGoNext: Boolean,
    isCheckBoxChecked: Boolean,
    dataStorage: DataStorage,
    email: String,
    password: String,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Button(
        onClick = {
            if (isRegisterPressed.not()) onRegisterPressed(true)
            if (canGoNext.not()) return@Button
            if (isCheckBoxChecked) {
                coroutineScope.launch {
                    dataStorage.saveString(KEY_EMAIL, email)
                    dataStorage.saveString(KEY_PASSWORD, password)
                }
            }
            val startActivityIntent = Intent(context, MyProfileActivity::class.java).apply {
                putExtra(KEY_EMAIL, email)
            }
            context.startActivity(startActivityIntent)
            context.getActivity()?.finish()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        ),
        border = BorderStroke(2.dp, Orange),
    ) {
        Text(
            text = stringResource(R.string.register_register),
            color = White,
            fontSize = 16.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp,
        )
    }
}

@Composable
private fun TextBetweenButtons() {
    Text(
        text = stringResource(R.string.register_or),
        color = White,
        fontSize = 18.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun ButtonGoogle() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonColors(
            containerColor = Color.White,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = ""
            )
            Text(
                text = stringResource(R.string.register_google),
                color = Gray2,
                fontSize = 16.sp,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
            )
        }
    }
}

@Composable
private fun TopBlock(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isRegisterPressed: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TextBlock()
            TextFieldAndCheckBoxBlock(
                email,
                onEmailChange,
                password,
                onPasswordChange,
                isRegisterPressed,
                checked,
                onCheckedChange,
            )
        }
    }
}

@Composable
private fun TextFieldAndCheckBoxBlock(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isRegisterPressed: Boolean,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextFieldBlock(email, onEmailChange, password, onPasswordChange, isRegisterPressed)
        CheckBoxBlock(checked, onCheckedChange)
    }
}

@Composable
private fun CheckBoxBlock(
    checked: Boolean, onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CheckBox(checked, onCheckedChange)
        CheckBoxDescription()
    }
}

@Composable
private fun CheckBoxDescription() {
    Text(
        text = stringResource(R.string.register_remember_me),
        color = Gray,
        fontSize = 14.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun CheckBox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxColors(
            checkedCheckmarkColor = White,
            uncheckedCheckmarkColor = Color.Transparent,
            checkedBoxColor = Color.Transparent,
            uncheckedBoxColor = Color.Transparent,
            disabledCheckedBoxColor = Color.Transparent,
            disabledUncheckedBoxColor = Color.Transparent,
            disabledIndeterminateBoxColor = Color.Transparent,
            checkedBorderColor = White,
            uncheckedBorderColor = White,
            disabledBorderColor = White,
            disabledUncheckedBorderColor = White,
            disabledIndeterminateBorderColor = White,
        )
    )
}

@Composable
private fun TextFieldBlock(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isRegisterPressed: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        TextFieldEmail(email, onEmailChange, isRegisterPressed)
        TextFieldPassword(password, onPasswordChange, isRegisterPressed)
    }
}

@Composable
private fun TextFieldPassword(
    password: String,
    onPasswordChange: (String) -> Unit,
    isRegisterPressed: Boolean,
) {
    val isModified = rememberSaveable { mutableStateOf(false) }
    val isError = checkPasswordValueValidity(password).not()
    TextField(
        value = password,
        onValueChange = {
            onPasswordChange.invoke(it)
            if (isModified.value.not()) isModified.value = true
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = stringResource(R.string.register_password), color = Gray
            )
        },
        supportingText = {
            if (isModified.value && isError || isRegisterPressed && isError) Text(
                text = stringResource(
                    R.string.register_error_password_text
                )
            )
        },
        isError = if (isModified.value || isRegisterPressed) isError else false,
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        colors = getTextFieldCustomColors(),
    )
}

fun checkPasswordValueValidity(password: String): Boolean {
    val checkLength = password.length >= 6
    return checkLength
}

@Composable
private fun TextFieldEmail(
    email: String,
    onEmailChange: (String) -> Unit,
    isRegisterPressed: Boolean,
) {
    val isModified = rememberSaveable { mutableStateOf(false) }
    val isError = checkEmailValueValidity(email).not()
    TextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            if (isModified.value.not()) isModified.value = true
        },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = stringResource(R.string.register_email),
                color = Gray,
            )
        },
        supportingText = {
            if (isModified.value && isError || isRegisterPressed && isError) {
                Text(text = stringResource(R.string.register_error_email_text))
            }
        },
        isError = if (isModified.value || isRegisterPressed) isError else false,
        singleLine = true,
        colors = getTextFieldCustomColors()
    )
}

fun checkEmailValueValidity(email: String): Boolean {
    val matches = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return matches
}

@Composable
private fun TextBlock() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextTop()
        TextBottom()
    }
}

@Composable
private fun TextBottom() {
    Text(
        text = stringResource(R.string.register_fill_out_the_profile_and_go_to_the_application),
        color = White,
        fontSize = 12.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.Normal,
    )
}

@Composable
private fun TextTop() {
    Text(
        text = stringResource(R.string.register_let_s_get_acquainted),
        color = White,
        fontSize = 24.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Preview(showBackground = true, device = Devices.PIXEL)
@Composable
fun SignUpScreenPreview() {
    SocialNetworkClientTheme {
        SignUpScreen()
    }
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}