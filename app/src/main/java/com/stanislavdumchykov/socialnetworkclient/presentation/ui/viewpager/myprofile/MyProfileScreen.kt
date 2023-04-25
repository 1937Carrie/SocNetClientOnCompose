package com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.myprofile

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
import com.stanislavdumchykov.socialnetworkclient.domain.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.domain.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.domain.utils.ScreenList
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyProfile(pagerState: PagerState, email: String) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    DrawBackGround()
    Column {
        DrawTopBlock(email.substring(0, email.indexOf('@')))
        DrawBottomBlock(pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawBottomBlock(pagerState: PagerState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Constants.PERCENT_60),
            verticalArrangement = Arrangement.Center,
        ) {
            DrawSocialNetworkLinksBlock()
        }
        Column(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.myprofile_padding))
                .fillMaxSize()
        ) {
            DrawButtonEditProfile()
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
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
        )
    }
}

@Composable
private fun DrawButtonEditProfile() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_editprofile_height))
            .background(color = Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable { }
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
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
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
private fun DrawTopBlock(email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(Constants.PERCENT_50),
    ) {
        DrawText()
        DrawUserInfo(email)
    }
}

@Composable
private fun DrawUserInfo(email: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.default_profile_image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(Constants.PERCENT_33)
                .aspectRatio(Constants.PERCENT_100)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_bigger)))
        val splittedEmail = email.split('.')
        Text(
            text = if (splittedEmail.size == 2) "${
                splittedEmail[0].replaceFirstChar {
                    it.titlecase(Locale.ROOT)
                }
            } ${
                splittedEmail[1].replaceFirstChar {
                    it.titlecase(Locale.ROOT)
                }
            }" else email,
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_username_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_profession),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding_smaller)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_address),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
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
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD.fontFamily,
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
                .fillMaxHeight(Constants.PERCENT_50)
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