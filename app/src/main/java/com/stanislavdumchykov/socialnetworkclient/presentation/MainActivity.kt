package com.stanislavdumchykov.socialnetworkclient.presentation

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import java.util.*

private const val PERCENT_33 = 0.33f
private const val PERCENT_50 = 0.5f
private const val PERCENT_60 = 0.6f
private const val PERCENT_100 = 1f

class MainActivity : ComponentActivity()

@Composable
fun DrawMyProfile(navController: NavController, email: String) {
    DrawBackGround()
    Column {
        DrawTopBlock(email.substring(0, email.indexOf('@')))
        DrawBottomBlock()
    }
}

@Composable
private fun DrawBottomBlock() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(PERCENT_60),
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
            DrawButtonViewMyContacts()
        }
    }
}

@Composable
private fun DrawButtonViewMyContacts() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_viewmycontacts_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .background(color = colorResource(R.color.custom_orange))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.myprofile_button_viewmycontacts).uppercase(),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
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
            color = colorResource(R.color.custom_gray_text),
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
    }
}

@Composable
private fun DrawSocialNetworkLinksBlock() {
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
            .fillMaxHeight(PERCENT_50),
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
                .fillMaxWidth(PERCENT_33)
                .aspectRatio(PERCENT_100)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.myprofile_spacer_bigger)))
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
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_profession),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding_smaller)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_address),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.myprofile_spacer_normal)))
    }
}

@Composable
private fun DrawText() {
    Text(
        text = stringResource(R.string.myprofile_text_settings),
        modifier = Modifier.padding(dimensionResource(R.dimen.myprofile_padding)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.myprofile_settings_text_fontsize).value.sp,
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
    )
}

@Composable
private fun DrawBackGround() {
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.custom_blue))
            .fillMaxWidth()
            .fillMaxHeight(PERCENT_50)
    )
}