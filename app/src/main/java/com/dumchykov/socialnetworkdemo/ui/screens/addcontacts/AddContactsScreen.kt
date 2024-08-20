package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.Gray828282
import com.dumchykov.socialnetworkdemo.ui.theme.GrayText
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddContactsScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: AddContactsViewModel = hiltViewModel(),
) {
    val addContactsState = viewModel.addContactsState.collectAsState().value
    val coroutineScope = rememberCoroutineScope()
    val lazyColumnState = rememberLazyListState()
    val showFab =
        remember { derivedStateOf { lazyColumnState.firstVisibleItemIndex > 0 } }
    val onAdd: (Int) -> Unit = { contactId -> viewModel.addToContactList(contactId) }
    val onNavigationArrowClick: () -> Unit = { navController.navigateUp() }

    AddContactsScreen(
        padding = padding,
        coroutineScope = coroutineScope,
        lazyColumnState = lazyColumnState,
        showFab = showFab,
        addContactState = addContactsState,
        onAdd = onAdd,
        onNavigationArrowClick = onNavigationArrowClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddContactsScreen(
    padding: PaddingValues,
    coroutineScope: CoroutineScope,
    lazyColumnState: LazyListState,
    showFab: State<Boolean>,
    addContactState: AddContactsState,
    onNavigationArrowClick: () -> Unit,
    onAdd: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Blue)
            .padding(
                start = padding.calculateStartPadding(LayoutDirection.Rtl),
                top = padding.calculateTopPadding(),
                end = padding.calculateEndPadding(LayoutDirection.Rtl),
            ),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Users",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationArrowClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description",
                            tint = White
                        )
                    }
                },
                windowInsets = WindowInsets(0.dp),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                ),
            )
        },
        floatingActionButton = {
            if (showFab.value) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Orange)
                        .clickable {
                            coroutineScope.launch {
                                lazyColumnState.scrollToItem(0, 0)
                            }
                        },
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                        tint = White
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = contentPadding.calculateTopPadding()
                )
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .background(Blue)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .padding(16.dp),
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
            ContactsColumn(
                lazyColumnState = lazyColumnState,
                addContactsState = addContactState,
                onAdd = onAdd
            )
        }
    }
}

@Composable
private fun ContactsColumn(
    lazyColumnState: LazyListState,
    addContactsState: AddContactsState,
    onAdd: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = lazyColumnState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(
            items = addContactsState.allContacts,
            key = { _, item -> item }
        ) { _, contact ->
            ItemContact(
                contact = contact,
                onAdd = onAdd
            )
        }
    }
}

@Composable
private fun ItemContact(
    contact: Contact,
    onAdd: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gray, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(White)
            .clickable(
                enabled = contact.isAdded.not(),
                onClick = { onAdd(contact.id) }
            )
            .padding(8.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image =
            if (contact.isAdded) {
                R.drawable.black_guy_happy
            } else {
                R.drawable.black_guy_disappointed
            }
        Image(
            painter = painterResource(image),
            modifier = Modifier
                .clip(CircleShape)
                .aspectRatio(1f),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = contact.name.orEmpty(),
                color = GrayText,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
            Text(
                text = contact.career.orEmpty(),
                color = Gray828282,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = OPENS_SANS
            )
        }
        when (contact.updateUiState) {
            true -> CircularProgressIndicator()
            false -> when (contact.isAdded) {
                true -> {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "",
                        modifier = Modifier.size(23.dp),
                        tint = Blue
                    )
                }

                false -> {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add",
                            color = Orange,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OPENS_SANS
                        )
                        Icon(
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(23.dp),
                            tint = Orange
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AddContactsScreenPreview() {
    AddContactsScreen(
        padding = PaddingValues(0.dp),
        coroutineScope = rememberCoroutineScope(),
        lazyColumnState = rememberLazyListState(),
        showFab = remember { mutableStateOf(false) },
        addContactState = AddContactsState(Contact.sampleList),
        onNavigationArrowClick = {},
        onAdd = {})
}