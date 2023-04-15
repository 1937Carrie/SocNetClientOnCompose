package com.stanislavdumchykov.socialnetworkclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.MainActivity.Fonts.FONT_OPENSANS_SEMI_BOLD

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawScreen()
        }
    }

    companion object Fonts {
        val FONT_OPENSANS_SEMI_BOLD = FontFamily(Font(R.font.opensans_semi_bold))
    }
}

@Composable
fun DrawScreen() {
    DrawBackGround()
    Column {
        DrawTopBlock()
        DrawBottomBlock()
    }
}

@Composable
fun DrawBottomBlock() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
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
fun DrawButtonViewMyContacts() {
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
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fotsize).value.sp,
            fontFamily = FONT_OPENSANS_SEMI_BOLD,
        )
    }
}

@Composable
fun DrawButtonEditProfile() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.myprofile_button_editprofile_height))
            .border(
                dimensionResource(R.dimen.myprofile_button_editprofile_border_width),
                colorResource(R.color.custom_blue),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            )
            .background(color = Color.Transparent)
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.myprofile_button_editprofile),
            color = colorResource(R.color.custom_gray_text),
            fontSize = dimensionResource(R.dimen.myprofile_button_editprofile_fotsize).value.sp,
            fontFamily = FONT_OPENSANS_SEMI_BOLD,
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
fun DrawTopBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
    ) {
        DrawText()
        DrawUserInfo()
    }
}

@Composable
fun DrawUserInfo() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.default_profile_image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .aspectRatio(1f)
                .clip(
                    CircleShape
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.myprofile_spacer_bigger)))
        Text(
            text = stringResource(R.string.myprofile_text_username),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.myprofile_username_text_fontsize).value.sp,
            fontFamily = FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_profession),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding_smaller)),
            color = colorResource(R.color.custom_gray),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = FONT_OPENSANS_SEMI_BOLD,
        )
        Text(
            text = stringResource(R.string.myprofile_text_user_address),
            modifier = Modifier.padding(top = dimensionResource(R.dimen.myprofile_padding)),
            color = colorResource(R.color.custom_gray),
            fontSize = dimensionResource(R.dimen.myprofile_user_profession_text_fontsize).value.sp,
            fontFamily = FONT_OPENSANS_SEMI_BOLD,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.myprofile_spacer_normal)))
    }
}

@Composable
fun DrawText() {
    Text(
        text = stringResource(R.string.myprofile_text_settings),
        modifier = Modifier.padding(dimensionResource(R.dimen.myprofile_padding)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.myprofile_settings_text_fontsize).value.sp,
        fontFamily = FONT_OPENSANS_SEMI_BOLD,
    )
}

@Composable
fun DrawBackGround() {
    Box(
        modifier = Modifier
            .background(color = colorResource(R.color.custom_blue))
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
    )
}