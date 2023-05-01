package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.contactlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Fonts
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.ScreenList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactList(
    onItemClick: (String, String, String) -> Unit,
    pagerState: PagerState,
    contactListViewModel: ContactListViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.custom_blue))
            .fillMaxSize()
    ) {
        val isOnThisScreen = remember { mutableStateOf(true) }

        DrawTopBlock(pagerState, isOnThisScreen)
        DrawAddContactsText()
        DrawContactList(onItemClick, contactListViewModel, isOnThisScreen)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DrawContactList(
    onItemClick: (String, String, String) -> Unit,
    contactListViewModel: ContactListViewModel,
    isOnThisScreen: MutableState<Boolean>
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
                    actionColor = colorResource(R.color.custom_orange), snackbarData = data
                )
            }
        },
        content = {
            val localUserList: List<LocalUser> by contactListViewModel.localUserList.collectAsState(
                emptyList()
            )
            val lazyListState = rememberLazyListState()
            val isMultiselect = rememberSaveable { mutableStateOf(false) }
            val selectedItemsList = remember { mutableListOf<LocalUser>() }

            if (!isOnThisScreen.value) {
                isMultiselect.value = false
                selectedItemsList.clear()
            }

            Column {
                val lazyColumnModifier = if (isMultiselect.value) Modifier.weight(1f) else Modifier
                LazyColumn(
                    modifier = lazyColumnModifier
                        .background(colorResource(R.color.white))
                        .padding(bottom = it.calculateBottomPadding()),
                    state = lazyListState,
                    content = {
                        items(items = localUserList,
                            key = { user -> user.id },
                            itemContent = { item: LocalUser ->
                                val currentItem by rememberUpdatedState(item)
                                val dismissState =
                                    rememberDismissState(confirmStateChange = { dismissValue ->
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
                                    })

                                SwipeToDismiss(state = dismissState,
                                    directions = if (isMultiselect.value) setOf() else setOf(
                                        DismissDirection.EndToStart
                                    ),
                                    background = {
//                                    SwipeBackground(dismissState)
                                    },
                                    dismissContent = {
                                        DrawItem(
                                            onItemClick = onItemClick,
                                            isMultiSelect = isMultiselect,
                                            selectedItemsList = selectedItemsList,
                                            localUser = item,
                                            coroutineScope = coroutineScope,
                                            scaffoldState = scaffoldState,
                                            contactListViewModel = contactListViewModel,
                                            index = item.id,
                                            scaffoldMessage = scaffoldMessage,
                                            scaffoldActionLabel = scaffoldActionLabel
                                        )
                                    })

                            })
                    },
                )
                if (isMultiselect.value) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 32.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Image(painter = painterResource(R.drawable.ic_multiselect_remove),
                            contentDescription = "",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    selectedItemsList.forEach {
                                        contactListViewModel.removeUser(it)
                                    }
                                    selectedItemsList.clear()
                                    isMultiselect.value = false
                                })
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawItem(
    onItemClick: (String, String, String) -> Unit,
    isMultiSelect: MutableState<Boolean>,
    selectedItemsList: MutableList<LocalUser>,
    localUser: LocalUser,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    scaffoldMessage: String,
    scaffoldActionLabel: String
) {
    val isSelect = rememberSaveable { mutableStateOf(false) }
    isSelect.value = selectedItemsList.contains(localUser)

    Box(
        modifier = Modifier
            .padding(
                horizontal = dimensionResource(R.dimen.spacer_smaller),
                vertical = dimensionResource(R.dimen.spacer_small),
            )
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.contactlist_item_height))
            .background(color = if (isMultiSelect.value) colorResource(R.color.contact_list_background) else Color.Transparent)
            .border(
                dimensionResource(R.dimen.border_width),
                colorResource(R.color.custom_gray_2),
                RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size))
            )
            .clip(RoundedCornerShape(dimensionResource(R.dimen.rounded_corner_size)))
            .combinedClickable(
                onLongClick = {
                    handleItemMultiSelectState(
                        isSelect, selectedItemsList, localUser, isMultiSelect
                    )
                },
                onClick = {
                    if (isMultiSelect.value) {
                        handleItemMultiSelectState(
                            isSelect, selectedItemsList, localUser, isMultiSelect
                        )
                    } else {
                        val name = if (localUser.name != "") localUser.name else "_"
                        val career = if (localUser.career != "") localUser.career else "_"
                        val address = if (localUser.address != "") localUser.address else "_"
                        onItemClick(name, career, address)
                    }
                })
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.spacer_small))
                .fillMaxSize(),
        ) {
            if (isMultiSelect.value) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = dimensionResource(R.dimen.spacer_small)),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(painter = if (isSelect.value) painterResource(R.drawable.ic_multiselect_checked)
                    else painterResource(R.drawable.ic_multiselect_unchecked),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                handleItemMultiSelectState(
                                    isSelect, selectedItemsList, localUser, isMultiSelect
                                )
                            })
                }
            }
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
                    text = localUser.name,
                    color = colorResource(R.color.contact_list_name_text_color),
                    fontSize = dimensionResource(R.dimen.contactlist_text_name_fontsize).value.sp,
                    fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
                )
                Text(
                    text = localUser.career,
                    color = colorResource(R.color.contact_list_career_text_color),
                    fontSize = dimensionResource(R.dimen.contactlist_text_career_fontsize).value.sp,
                    fontFamily = Fonts.FONT_OPEN_SANS.fontFamily,
                )
            }
            if (!isMultiSelect.value) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd
                ) {
                    Image(painter = painterResource(R.drawable.ic_delete_bucket),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            removeContact(
                                coroutineScope,
                                scaffoldState,
                                contactListViewModel,
                                index,
                                localUser,
                                scaffoldMessage,
                                scaffoldActionLabel
                            )
                        })
                }
            }
        }
    }
}

private fun handleItemMultiSelectState(
    isSelect: MutableState<Boolean>,
    selectedItemsList: MutableList<LocalUser>,
    localUser: LocalUser,
    isMultiSelect: MutableState<Boolean>,
) {
    isSelect.value = !isSelect.value

    if (isSelect.value) {
        selectedItemsList.add(localUser)
    } else {
        selectedItemsList.remove(localUser)
    }

    isMultiSelect.value = selectedItemsList.isNotEmpty()
}


private fun removeContact(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    localUser: LocalUser,
    scaffoldMessage: String,
    scaffoldActionLabel: String,
) {

    contactListViewModel.removeUser(localUser)

    restoreContact(
        coroutineScope,
        scaffoldState,
        contactListViewModel,
        index,
        localUser,
        scaffoldMessage,
        scaffoldActionLabel
    )

}

private fun restoreContact(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    contactListViewModel: ContactListViewModel,
    index: Int,
    localUser: LocalUser,
    scaffoldMessage: String,
    scaffoldActionLabel: String,
) {
    coroutineScope.launch {
        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
            message = scaffoldMessage,
            actionLabel = scaffoldActionLabel,
        )
        when (snackbarResult) {
            SnackbarResult.Dismissed -> {}
            SnackbarResult.ActionPerformed -> {
                contactListViewModel.addUser(index, localUser)
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
            icon, contentDescription = "Localized description", modifier = Modifier.scale(scale)
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
        fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DrawTopBlock(pagerState: PagerState, isOnThisScreen: MutableState<Boolean>) {
    val coroutineScope = rememberCoroutineScope()

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
                isOnThisScreen.value = false
                coroutineScope.launch {
                    pagerState.scrollToPage(ScreenList.MY_PROFILE.ordinal)
                }
            })
        Text(
            text = stringResource(R.string.contactlist_contacts_text),
            color = colorResource(R.color.custom_white),
            fontSize = dimensionResource(R.dimen.contactlist_contacts_font_size).value.sp,
            fontFamily = Fonts.FONT_OPEN_SANS_SEMI_BOLD.fontFamily,
        )
        Image(
            painter = painterResource(R.drawable.ic_search), contentDescription = ""
        )
    }
}