package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.SharedViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.signup.SignUpViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Response
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Status


@Composable
fun EditProfileScreen(
    onClick: () -> Unit,
    sharedViewModel: SharedViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white))
    ) {
        var textFieldList: List<Pair<String, MutableState<String>>>

        with(sharedViewModel.user) {
            textFieldList = listOf(
                stringResource(R.string.editprofile_text_username) to rememberSaveable {
                    mutableStateOf(
                        this.name.orEmpty()
                    )
                },
                stringResource(R.string.editprofile_text_career) to rememberSaveable {
                    mutableStateOf(
                        this.career.orEmpty()
                    )
                },
                stringResource(R.string.editprofile_text_phone) to rememberSaveable {
                    mutableStateOf(
                        this.phone.orEmpty()
                    )
                },
                stringResource(R.string.editprofile_text_address) to rememberSaveable {
                    mutableStateOf(
                        this.address.orEmpty()
                    )
                },
                stringResource(R.string.editprofile_text_dateofbirth) to rememberSaveable {
                    mutableStateOf(
                        this.birthday.orEmpty()
                    )
                },
            )
        }

        DrawTopBlock(onClick)
        DrawDataBlock(Modifier.weight(1f), textFieldList)
        DrawButtonBlock(onClick, textFieldList, sharedViewModel)
    }
}


@Composable
private fun DrawTopBlock(onArrowClick: () -> Unit) {
    Column(
        modifier = Modifier.background(colorResource(R.color.custom_blue))
    ) {
        DrawText(onArrowClick)
        DrawPictures()
    }
}

@Composable
private fun DrawText(onArrowClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.spacer_smaller))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
//                    clearAllData()
                    onArrowClick()
                }
        )
        Text(
            text = stringResource(R.string.editprofile_text_edit_profile),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_settings_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "",
            alpha = 0f,
        )
    }
}

@Composable
private fun DrawPictures() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.spacer_normal)),
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
private fun DrawDataBlock(
    modifier: Modifier,
    textFieldList: List<Pair<String, MutableState<String>>>
) {

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        content = {
            items(textFieldList.size) {
                DrawItem(textFieldList[it].first, textFieldList[it].second)
            }
        }
    )
}

@Composable
fun DrawItem(label: String, value: MutableState<String>) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(R.dimen.spacer_smaller),
                vertical = dimensionResource(R.dimen.spacer_small)
            ),
        label = {
            Text(
                text = label,
                color = colorResource(R.color.custom_gray_2)
            )
        },
        keyboardOptions = if (label == stringResource(R.string.editprofile_text_phone))
            KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        else
            KeyboardOptions.Default,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(R.color.custom_gray_text),
            backgroundColor = Color.Transparent,
            cursorColor = colorResource(R.color.custom_gray_text),
            focusedIndicatorColor = colorResource(R.color.custom_gray_4),
            unfocusedIndicatorColor = colorResource(R.color.custom_gray_4),
            disabledIndicatorColor = colorResource(R.color.custom_gray_4),
        )
    )
}

@Composable
private fun DrawButtonBlock(
    onSaveClick: () -> Unit,
    textFieldList: List<Pair<String, MutableState<String>>>,
    sharedViewModel: SharedViewModel,
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    val statusNetwork = signUpViewModel.statusNetwork.observeAsState()
    if (statusNetwork.value?.status == Status.SUCCESS) {
        with(signUpViewModel.user.value) {
            if (this != null) sharedViewModel.addUser(this)
        }
        onSaveClick()
        signUpViewModel.clearAllStatuses()
    }

    if (signUpViewModel.user.value == null) {
        signUpViewModel.setUser(sharedViewModel.user)
    }

    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.spacer_smaller),
                vertical = dimensionResource(R.dimen.spacer_under_normal)
            )
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_viewmycontacts_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .background(color = colorResource(R.color.custom_orange))
            .clickable {
                signUpViewModel.editProfile(
                    textFieldList[0].second.value,
                    textFieldList[1].second.value,
                    textFieldList[2].second.value,
                    textFieldList[3].second.value,
                    textFieldList[4].second.value,
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.editprofile_text_save).uppercase(),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.editprofile_button_save_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
    }
}