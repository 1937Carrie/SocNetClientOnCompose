package com.stanislavdumchykov.socialnetworkclient.presentation.screens.my_profile

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.signature.ObjectKey
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.data.DataStorage
import com.stanislavdumchykov.socialnetworkclient.presentation.MainActivity
import com.stanislavdumchykov.socialnetworkclient.presentation.screens.sign_up.getActivity
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Blue
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Gray
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Gray2
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.Orange
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.SocialNetworkClientTheme
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.White
import com.stanislavdumchykov.socialnetworkclient.presentation.ui.theme.fontOpenSans
import com.stanislavdumchykov.socialnetworkclient.util.KEY_EMAIL
import com.stanislavdumchykov.socialnetworkclient.util.KEY_PASSWORD
import kotlinx.coroutines.launch

@Composable
fun MyProfileScreen(
    email: String,
    modifier: Modifier = Modifier
) {
    val formattedName = formatEmail(email)
    Column(modifier = modifier.fillMaxSize()) {
        val innerModifier = Modifier.weight(5f)
        TopBlock(formattedName, innerModifier)
        BottomBlock(innerModifier)
    }
}

fun formatEmail(email: String): String {
    return email.substringBefore("@")
        .split(".")
        .joinToString(" ") { it ->
            it.lowercase().replaceFirstChar { it.lowercase() }
        }
}

@Composable
private fun BottomBlock(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(White)
            .fillMaxSize()
            .padding(16.dp),
    ) {
        IconsSocialNetworks(modifier.weight(1f))
        ButtonsBottom(modifier)
    }

}

@Composable
private fun ButtonsBottom(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonEditProfile(modifier)
        ButtonViewMyContacts(modifier)
    }
}

@Composable
private fun ButtonViewMyContacts(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonColors(
            containerColor = Orange,
            contentColor = Orange,
            disabledContainerColor = Orange,
            disabledContentColor = Orange,
        ),
    ) {
        Text(
            text = stringResource(R.string.my_profile_view_my_contacts),
            color = White,
            fontSize = 16.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun ButtonEditProfile(modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonColors(
            containerColor = White,
            contentColor = White,
            disabledContainerColor = White,
            disabledContentColor = White,
        ),
        border = BorderStroke(2.dp, Blue),
    ) {
        Text(
            text = stringResource(R.string.my_profile_edit_profile),
            color = Gray2,
            fontSize = 14.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun IconsSocialNetworks(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_facebook),
            contentDescription = stringResource(R.string.my_profile_icon_facebook)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_linkedin),
            contentDescription = stringResource(R.string.my_profile_icon_linkedin)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_instagram),
            contentDescription = stringResource(R.string.my_profile_icon_instagram)
        )
    }
}

@Composable
private fun TopBlock(
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Blue)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar()
        MainContent(name)
    }
}

@Composable
private fun MainContent(name: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ProfileImage()
        AllText(name)
    }
}

@Composable
private fun AllText(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileName(name)
            ProfileProfession()
        }
        ProfileAddress()
    }
}

@Composable
private fun ProfileAddress() {
    Text(
        text = stringResource(R.string.my_profile_address),
        color = Gray,
        fontSize = 14.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun ProfileProfession() {
    Text(
        text = stringResource(R.string.my_profile_profession),
        color = Gray,
        fontSize = 14.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
private fun ProfileName(name: String) {
    Text(
        text = name,
        color = White,
        fontSize = 18.sp,
        fontFamily = fontOpenSans,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProfileImage() {
    val requestManager: RequestManager = Glide.with(LocalContext.current)
    val signature = ObjectKey(System.currentTimeMillis().toString())

    val imageUrl =
        "https://s3-alpha-sig.figma.com/img/d3c6/c0f0/deeb8f5d8ac8b3780b8ad0d1791ed9e6?Expires=1716768000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=aFd2IsPKpxtiQOqGE9ABzCehhlueduBwUGze6BvBDSWYL5Qb4C7arAWI5zHhvVO~K-brCWpPVZRAHgfRs6vY6uiURE2OH0N52GHb83A-2940j43CakL4cp08BQIFxdwOiHiPgAMbCC95dwlt7qrN3SW1ww1WB4I0lsjuA1PouC432HjIbY73UsQRAqm2qvkbSPnIuSr~Lt-QG6UsBaTvNbO4AysRrF-NVk9HlJKyOT11Jf1Cqnmx0AwEVkfx-rM7ZHBl2CjN6ezTqk0q64rXc76JpP5CujWgdgzDQsUQkZYW8okZX5p6M-VJ91dzIWIgV9ciWHKGLzZ9nF7ViaVDrA__"
    GlideImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.my_profile_my_profile_screen),
        modifier = Modifier
            .fillMaxWidth(1f / 3f)
            .aspectRatio(1f)
            .clickable(onClick = {/*TODO*/ })
            .fillMaxSize(),
        contentScale = ContentScale.Crop,
    ) {
        it
            .placeholder(R.drawable.ic_launcher_foreground)
            .thumbnail(
                requestManager
                    .asDrawable()
                    .load(imageUrl)
                    .signature(signature)
            )
            .signature(signature)
            .transform(CircleCrop())
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.settings),
            color = White,
            fontSize = 24.sp,
            fontFamily = fontOpenSans,
            fontWeight = FontWeight.SemiBold,
        )
        val context = LocalContext.current
        val dataStorage = DataStorage(context)
        val rememberCoroutineScope = rememberCoroutineScope()
        OutlinedButton(
            onClick = {
                rememberCoroutineScope.launch {
                    dataStorage.saveString(KEY_EMAIL, "")
                    dataStorage.saveString(KEY_PASSWORD, "")
                    val startActivityIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(startActivityIntent)
                    context.getActivity()?.finish()
                }
            },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(2.dp, Gray)
        ) {
            Text(
                text = stringResource(R.string.log_out),
                color = Gray,
                fontSize = 14.sp,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyProfileScreenPreview() {
    SocialNetworkClientTheme {
        MyProfileScreen("test@email.com")
    }
}