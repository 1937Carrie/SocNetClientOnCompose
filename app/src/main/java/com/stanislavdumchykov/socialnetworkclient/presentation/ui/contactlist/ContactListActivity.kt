package com.stanislavdumchykov.socialnetworkclient.presentation.ui.contactlist

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavController
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListActivity : ComponentActivity() {

}

@Composable
fun ContactList(
    navController: NavController,
    contactListViewModel: ContactListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.custom_blue))
            .fillMaxSize()
    ) {
        DrawTopBlock()
        DrawAddContactsText()
        DrawContactList(contactListViewModel)
    }
}

@Composable
private fun DrawContactList(contactListViewModel: ContactListViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.contact_list_background)),
    ) {
        val users = mutableStateOf(contactListViewModel.userList.toList())
        itemsIndexed(users.value) { _, user ->
            Box(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.spacer_smaller),
                        vertical = dimensionResource(R.dimen.spacer_small),
                    )
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.contactlist_item_height))
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
                            text = user.name,
                            color = colorResource(R.color.contact_list_name_text_color),
                            fontSize = dimensionResource(R.dimen.contactlist_text_name_fontsize).value.sp,
                            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
                        )
                        Text(
                            text = user.career,
                            color = colorResource(R.color.contact_list_career_text_color),
                            fontSize = dimensionResource(R.dimen.contactlist_text_career_fontsize).value.sp,
                            fontFamily = Fonts.FONT_OPENSANS,
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_delete_bucket),
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                contactListViewModel.removeUser(user)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DrawAddContactsText() {
    Text(
        text = stringResource(R.string.contactlist_add_contacts_text),
        modifier = Modifier.padding(dimensionResource(R.dimen.spacer_smaller)),
        color = colorResource(R.color.custom_white),
        fontSize = dimensionResource(R.dimen.contactlist_add_contacts_font_size).value.sp,
        fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
    )
}

@Composable
private fun DrawTopBlock() {
    Row(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.spacer_smaller))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Image(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.contactlist_contacts_text),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.contactlist_contacts_font_size).value.sp,
            fontFamily = Fonts.FONT_OPENSANS_SEMI_BOLD,
        )
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = ""
        )
    }
}