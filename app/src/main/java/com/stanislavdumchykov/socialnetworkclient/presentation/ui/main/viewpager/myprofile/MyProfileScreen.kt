package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.myprofile

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.presentation.SharedViewModel
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.ScreenList
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyProfile(
    pagerState: PagerState,
    sharedViewModel: SharedViewModel,
    onEditProfileClick: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    DrawBackGround()
    Column {
        DrawTopBlock(sharedViewModel.user)
        DrawBottomBlock(pagerState, onEditProfileClick)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawBottomBlock(pagerState: PagerState, onEditProfileClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Constants.PERCENT_060),
            verticalArrangement = Arrangement.Center,
        ) {
            DrawSocialNetworkLinksBlock()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.myprofile_padding))
                .fillMaxSize()
        ) {
            DrawButtonEditProfile(onEditProfileClick)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.myprofile_padding)))
            DrawButtonViewMyContacts(pagerState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawButtonViewMyContacts(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_viewmycontacts_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .background(color = colorResource(R.color.custom_orange))
            .clickable {
                coroutineScope.launch {
                    pagerState.scrollToPage(ScreenList.CONTACT_LIST.ordinal)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.myprofile_button_viewmycontacts).uppercase(),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
    }
}

@Composable
private fun DrawButtonEditProfile(onEditProfileClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_editprofile_height))
            .background(color = Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable { onEditProfileClick() }
            .border(
                dimensionResource(R.dimen.myprofile_button_editprofile_border_width),
                colorResource(R.color.custom_blue),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.myprofile_button_editprofile),
            color = if (isSystemInDarkTheme()) colorResource(R.color.white) else colorResource(R.color.custom_gray_text),
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
    }
}

@Composable
fun DrawSocialNetworkLinksBlock() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_facebook),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { },
        )
        Image(
            painter = painterResource(R.drawable.ic_linkedin),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { },
        )
        Image(
            painter = painterResource(R.drawable.ic_instagram),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .clickable { },
        )
    }
}

@Composable
private fun DrawTopBlock(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(Constants.PERCENT_050),
    ) {
        DrawText()
        DrawUserInfo(user)
    }
}

@Composable
private fun DrawUserInfo(user: User) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.default_profile_image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(Constants.PERCENT_033)
                .aspectRatio(Constants.PERCENT_100)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_bigger)))
        Text(
            text = user.name ?: "",
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_username_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_profession),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding_smaller)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_address),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_normal)))
    }
}

@Composable
private fun DrawText() {
    Text(
        text = stringResource(R.string.myprofile_text_settings),
        modifier = Modifier.padding(dimensionResource(R.dimen.myprofile_padding)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.myprofile_settings_text_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
    )
}

@Composable
private fun DrawBackGround() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Constants.PERCENT_050)
                .background(color = colorResource(R.color.custom_blue))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = if (isSystemInDarkTheme()) colorResource(R.color.custom_background_dark) else colorResource(
                        R.color.white
                    )
                ),
        )
    }
}