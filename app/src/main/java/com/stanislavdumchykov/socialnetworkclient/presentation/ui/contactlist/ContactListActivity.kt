package com.stanislavdumchykov.socialnetworkclient.presentation.ui.contactlist

import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.User
import com.stanislavdumchykov.socialnetworkclient.presentation.navigation.Routes
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
        DrawTopBlock(navController)
        DrawAddContactsText()
        DrawContactList(navController, contactListViewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DrawContactList(
    navController: NavController,
    contactListViewModel: ContactListViewModel
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val scaffoldMessage = stringResource(R.string.contactlist_scaffold_message_text)
    val scaffoldActionLabel = stringResource(R.string.contactlist_scaffold_actionLabel_text)

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    actionColor = colorResource(R.color.custom_orange),
                    snackbarData = data
                )
            }
        },
        content = {
            val userList: List<User> by contactListViewModel.userList.collectAsState(emptyList())
            val lazyListState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.contact_list_background))
                    .padding(bottom = it.calculateBottomPadding()),
                state = lazyListState,
                content = {
                    items(
                        items = userList,
                        key = { user -> user.id },
                        itemContent = { item: User ->
                            val currentItem by rememberUpdatedState(item)
                            val dismissState = rememberDismissState(
                                confirmStateChange = { dismissValue ->
                                    if (dismissValue == DismissValue.DismissedToStart) {
                                        coroutineScope.launch {
                                            removeContact(
                                                coroutineScope,
                                                scaffoldState,
                                                contactListViewModel,
                                                item.id,
                                                currentItem,
                                                scaffoldMessage,
                                                scaffoldActionLabel
                                            )
                                        }
                                        true
                                    } else false
                                }
                            )

                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.EndToStart),
                                background = {
//                                    SwipeBackground(dismissState)
                                },
                                dismissContent = {
                                    DrawItem(
                                        navController = navController,
                                        user = item,
                                        coroutineScope = coroutineScope,
                                        scaffoldState = scaffoldState,
                                        contactListViewModel = contactListViewModel,
                                        index = item.id,
                                        scaffoldMessage = scaffoldMessage,
                                        scaffoldActionLabel = scaffoldActionLabel
                                    )
                                }
                            )

                        }
                    )
                },
            )
        },
    )
}

@Composable
private fun DrawItem(
    navController: NavController,
    user: User,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    scaffoldMessage: String,
    scaffoldActionLabel: String
) {
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
                val name = if (user.name != "") user.name else "_"
                val career = if (user.career != "") user.career else "_"
                val address = if (user.address != "") user.address else "_"

                navController.navigate("${Routes.ContactProfile.route}/${name}/${career}/${address}")
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
                        removeContact(
                            coroutineScope,
                            scaffoldState,
                            contactListViewModel,
                            index,
                            user,
                            scaffoldMessage,
                            scaffoldActionLabel
                        )
                    }
                )
            }
        }
    }
}


private fun removeContact(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    user: User,
    scaffoldMessage: String,
    scaffoldActionLabel: String,
) {

    contactListViewModel.removeUser(user)

    restoreContact(
        coroutineScope,
        scaffoldState,
        contactListViewModel,
        index,
        user,
        scaffoldMessage,
        scaffoldActionLabel
    )

}

private fun restoreContact(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    user: User,
    scaffoldMessage: String,
    scaffoldActionLabel: String,
) {
    coroutineScope.launch {
        val snackbarResult =
            scaffoldState.snackbarHostState.showSnackbar(
                message = scaffoldMessage,
                actionLabel = scaffoldActionLabel,
            )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                contactListViewModel.addUser(index, user)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Green
            DismissValue.DismissedToStart -> Color.Red
        }
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Done
        DismissDirection.EndToStart -> Icons.Default.Delete
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = dimensionResource(R.dimen.spacer_smaller)),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = "Localized description",
            modifier = Modifier.scale(scale)
        )
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
private fun DrawTopBlock(navController: NavController) {
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
                    navController.popBackStack()
                }
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