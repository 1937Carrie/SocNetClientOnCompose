package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.alluserslist

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Status

@Composable
fun AllUsersListScreen(onArrowClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.custom_blue))
            .fillMaxSize()
    ) {
        DrawTopBlock(onArrowClick)
        DrawAllUsersList()
    }
}

@Composable
private fun DrawTopBlock(onArrowClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.spacer_smaller))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "",
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) {
                onArrowClick()
            })
        Text(
            text = stringResource(R.string.alluserslist_text_users),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.contactlist_contacts_font_size).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Image(
            painter = painterResource(R.drawable.ic_search), contentDescription = ""
        )
    }
}

@Composable
private fun DrawAllUsersList() {
    val allUsersListViewModel: AllUsersListViewModel = hiltViewModel()

    val allUsersList = allUsersListViewModel.allUsers.collectAsState(emptyList())
    val statusUserData = allUsersListViewModel.statusNetwork.observeAsState()
    allUsersListViewModel.getAllUsers()
    if (statusUserData.value?.status == Status.SUCCESS) {
        Log.d("ContactListScreen", "OnSuccessStatus: ${allUsersList.value}")
    }

    LazyColumn(
        modifier = Modifier.background(colorResource(R.color.white)),
        content = {
            itemsIndexed(
                items = allUsersList.value,
                itemContent = { index: Int, user: User -> DrawItem(networkUser = user) })
        }
    )
}

@Composable
private fun DrawItem(
    networkUser: User,
) {
    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.spacer_smaller),
                vertical = dimensionResource(R.dimen.spacer_small),
            )
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.contactlist_item_height))
            .background(colorResource(R.color.white))
            .border(
                dimensionResource(R.dimen.border_width),
                colorResource(R.color.custom_gray_2),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            )
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.spacer_small))
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(R.drawable.default_profile_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )
            Column(
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.spacer_small))
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = networkUser.name.orEmpty(),
                    color = colorResource(R.color.contact_list_name_text_color),
                    fontSize = dimensionResource(R.dimen.contactlist_text_name_fontsize).value.sp,
                    fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
                )
                Text(
                    text = networkUser.career.orEmpty(),
                    color = colorResource(R.color.contact_list_career_text_color),
                    fontSize = dimensionResource(R.dimen.contactlist_text_career_fontsize).value.sp,
                    fontFamily = Fonts.FONT_OPEN_SANS.fontFamily,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.alluserslist_text_add),
                    color = colorResource(R.color.custom_orange),
                    fontSize = dimensionResource(R.dimen.alluserslist_text_add_fontsize).value.sp,
                    fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.spacer_small)))
                Image(painter = painterResource(R.drawable.ic_add), contentDescription = "")

            }
        }
    }
}