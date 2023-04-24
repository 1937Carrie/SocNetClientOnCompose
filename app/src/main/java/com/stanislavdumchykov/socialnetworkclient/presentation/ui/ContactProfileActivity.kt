package com.stanislavdumchykov.socialnetworkclient.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts

class ContactProfileActivity {
}

@Composable
fun ContactProfile(
    navController: NavController,
    name: String,
    career: String,
    address: String,
) {
    Column {
        DrawTopBlock(navController, name, career, address)
        DrawBottomBlock()
    }
}

@Composable
private fun DrawTopBlock(
    navController: NavController,
    name: String,
    career: String,
    address: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(Constants.PERCENT_50)
            .background(color = colorResource(R.color.custom_blue)),
    ) {
        DrawText(navController)
        DrawUserInfo(name, career, address)
    }
}

@Composable
private fun DrawText(navController: NavController) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.spacer_smaller))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "",
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.popBackStack()
                }
        )
        Text(
            text = stringResource(R.string.contactprofile_profile_text),
            modifier = Modifier.padding(dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_settings_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "",
            alpha = 0f,
        )
    }
}

@Composable
private fun DrawUserInfo(name: String, career: String, address: String) {
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
        Text(
            text = name,
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_username_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = career,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding_smaller)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = address,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_gray_2),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_normal)))
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
                .fillMaxHeight(Constants.PERCENT_60),
            verticalArrangement = Arrangement.Center,
        ) {
            DrawSocialNetworkLinksBlock()
        }
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.myprofile_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            DrawButtonMessage()
        }
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
private fun DrawButtonMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_viewmycontacts_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .background(color = colorResource(R.color.custom_orange))
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.contactprofile_message_text).uppercase(),
            color = colorResource(R.color.white),
            fontSize = dimensionResource(R.dimen.contactprofile_button_message_fontsize).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
    }
}