package com.dumchykov.socialnetworkdemo.ui.screens.editprofile

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.GrayBDBDBD
import com.dumchykov.socialnetworkdemo.ui.theme.GrayText
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import com.dumchykov.socialnetworkdemo.ui.util.customTextFieldsColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditProfileScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val editProfileState = viewModel.editProfileState.collectAsState().value
    val updateState: (EditProfileState) -> Unit =
        { updatedState -> viewModel.updateState { updatedState } }
    val onArrowBackClick: () -> Unit = { navController.navigateUp() }
    val onSaveClick = { viewModel.editContact() }

    var showDatePickerState by remember { mutableStateOf(false) }

    val showDatePicker = { showDatePickerState = true }
    val onDatePickerDismiss = { showDatePickerState = false }

    var selectedDate by remember { mutableStateOf("") }
    val onDateSelected: (Long?) -> Unit = { date ->
        date?.let { date ->
            selectedDate = convertMillisToDate(date)
            viewModel.updateState { copy(dateOfBirth = viewModel.convertToCalendar(selectedDate)) }
        }
    }

    val initDateValue = editProfileState.dateOfBirth.timeInMillis

    LaunchedEffect(editProfileState.dataHasActualState) {
        if (editProfileState.dataHasActualState.not()) return@LaunchedEffect

        selectedDate = viewModel.convertCalendarToString(editProfileState.dateOfBirth)
    }

    EditProfileScreen(
        editProfileState = editProfileState,
        updateState = updateState,
        showDatePicker = showDatePicker,
        selectedDate = selectedDate,
        onArrowBackClick = onArrowBackClick,
        onSaveClick = onSaveClick
    )

    if (showDatePickerState) {
        DatePickerModal(
            initСapability = editProfileState.dataHasActualState,
            initDateValue = initDateValue,
            onDateSelected = onDateSelected,
            onDismiss = onDatePickerDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileScreen(
    editProfileState: EditProfileState,
    updateState: (EditProfileState) -> Unit,
    showDatePicker: () -> Unit,
    selectedDate: String,
    onArrowBackClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Edit profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onArrowBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
        }
    ) { innerPaddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            Box(
                Modifier
                    .background(Blue)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.black_guy_happy),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth(0.4f)
                        .aspectRatio(1f)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DialogTextField(
                    editProfileState.name,
                    { name -> updateState(editProfileState.copy(name = name)) },
                    "Username"
                )
                DialogTextField(
                    editProfileState.career,
                    { career -> updateState(editProfileState.copy(career = career)) },
                    "Career"
                )
                DialogTextField(
                    editProfileState.email,
                    { email -> updateState(editProfileState.copy(email = email)) },
                    "Email"
                )
                DialogTextField(
                    editProfileState.phone,
                    { phone -> updateState(editProfileState.copy(phone = phone)) },
                    "Phone"
                )
                DialogTextField(
                    editProfileState.address,
                    { address -> updateState(editProfileState.copy(address = address)) },
                    "Address",
                    false
                )
                TextField(
                    value = selectedDate,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Date of birth", color = Gray) },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = showDatePicker) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date",
                                tint = Gray
                            )
                        }
                    },
                    colors = customTextFieldsColors(
                        focusedTextColor = GrayText,
                        unfocusedTextColor = GrayText,
                        disabledTextColor = GrayText,
                        errorTextColor = GrayText,
                        cursorColor = GrayText,
                        errorCursorColor = GrayText,
                        selectionColors = TextSelectionColors(GrayBDBDBD, GrayBDBDBD.copy(0.4f)),
                        focusedIndicatorColor = GrayBDBDBD,
                        unfocusedIndicatorColor = GrayBDBDBD,
                        disabledIndicatorColor = GrayBDBDBD,
                        errorIndicatorColor = GrayBDBDBD
                    )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonColors(Orange, Color.Transparent, Orange, Color.Transparent)
                ) {
                    Text(
                        text = "Save",
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initСapability: Boolean,
    initDateValue: Long,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(initСapability) {
        if (initСapability.not()) return@LaunchedEffect
        datePickerState.selectedDateMillis = initDateValue
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
private fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    last: Boolean = false,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, color = Gray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = if (last) ImeAction.Done else ImeAction.Next),
        colors = customTextFieldsColors(
            focusedTextColor = GrayText,
            unfocusedTextColor = GrayText,
            disabledTextColor = GrayText,
            errorTextColor = GrayText,
            cursorColor = GrayText,
            errorCursorColor = GrayText,
            selectionColors = TextSelectionColors(GrayBDBDBD, GrayBDBDBD.copy(0.4f)),
            focusedIndicatorColor = GrayBDBDBD,
            unfocusedIndicatorColor = GrayBDBDBD,
            disabledIndicatorColor = GrayBDBDBD,
            errorIndicatorColor = GrayBDBDBD
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    EditProfileScreen(
        editProfileState = EditProfileState(),
        updateState = { _ -> },
        showDatePicker = {},
        selectedDate = "",
        onArrowBackClick = {},
        onSaveClick = {}
    )
}