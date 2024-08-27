package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dumchykov.contactsprovider.data.ContactsProvider
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.AddContacts
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.models.MyContactsContact
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.models.toMyContactsContact
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.Gray828282
import com.dumchykov.socialnetworkdemo.ui.theme.GrayBDBDBD
import com.dumchykov.socialnetworkdemo.ui.theme.GrayE7E7E7
import com.dumchykov.socialnetworkdemo.ui.theme.GrayText
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import com.dumchykov.socialnetworkdemo.ui.util.customTextFieldsColors
import kotlinx.coroutines.launch

@Composable
fun MyContactsScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MyContactsViewModel = hiltViewModel(),
    onNavigationArrowClick: () -> Unit = {},
) {
    BackHandler {
        onNavigationArrowClick()
    }

    LaunchedEffect(true) {
        viewModel.updateListOnEnter()
    }

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val myContactsState = viewModel.myContactsState.collectAsState().value
    val updateState: (MyContactsState) -> Unit = { state -> viewModel.updateState { state } }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val addContactState = remember { mutableStateOf(false) }
    val deleteContact: (Int) -> Unit = { contact -> viewModel.deleteContact(contact) }
    val deleteSelected: () -> Unit = { viewModel.deleteSelected() }
    val addContact: (MyContactsContact) -> Unit = { contact -> viewModel.addContact(contact) }
    val changeContactSelectedState: (MyContactsContact) -> Unit =
        { contact -> viewModel.changeContactSelectedState(contact) }
    val navigateToAddContacts: () -> Unit = { navController.navigate(AddContacts) }
    val navigateToDetail: (Int) -> Unit =
        { contactId -> navController.navigate(Detail(contactId)) }

    MyContactsScreen(
        padding = padding,
        updateState = updateState,
        snackbarHostState = snackbarHostState,
        myContactsState = myContactsState,
        addContactState = addContactState,
        deleteContact = deleteContact,
        deleteSelected = deleteSelected,
        addContact = addContact,
        changeContactSelectedState = changeContactSelectedState,
        navigateToAddContacts = navigateToAddContacts,
        onNavigationArrowClick = onNavigationArrowClick,
        navigateToDetail = navigateToDetail
    )

    val deleteContactState = viewModel.deleteContactState.collectAsState(initial = null).value
    LaunchedEffect(deleteContactState) {
        if (deleteContactState != null) {
            coroutineScope.launch {
                // Check if a snackbar is currently being displayed
                snackbarHostState.currentSnackbarData?.dismiss()

                val result = snackbarHostState
                    .showSnackbar(
                        message = "${deleteContactState.name} has been deleted",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Long
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        addContact(deleteContactState)
                    }

                    SnackbarResult.Dismissed -> {
                        /* Handle snackbar dismissed */
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyContactsScreen(
    padding: PaddingValues,
    updateState: (MyContactsState) -> Unit,
    snackbarHostState: SnackbarHostState,
    myContactsState: MyContactsState,
    addContactState: MutableState<Boolean>,
    deleteContact: (Int) -> Unit,
    deleteSelected: () -> Unit,
    addContact: (MyContactsContact) -> Unit,
    changeContactSelectedState: (MyContactsContact) -> Unit,
    onNavigationArrowClick: () -> Unit,
    navigateToAddContacts: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Blue)
            .padding(
                start = padding.calculateStartPadding(LayoutDirection.Rtl),
                end = padding.calculateEndPadding(LayoutDirection.Rtl)
            ),
        topBar = {
            when (myContactsState.searchState) {
                true -> {
                    Row(
                        modifier = Modifier
                            .background(Blue)
                            .padding(horizontal = 16.dp)
                            .height(64.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var isFocused by remember { mutableStateOf(false) }
                        BasicTextField(
                            value = myContactsState.searchQuery,
                            onValueChange = { updateState(myContactsState.copy(searchQuery = it)) },
                            modifier = Modifier
                                .height(40.dp)
                                .weight(1f)
                                .background(color = GrayText, shape = RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .onFocusChanged { focusState ->
                                    isFocused = focusState.isFocused
                                },
                            textStyle = TextStyle(
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = OPENS_SANS
                            ),
                            cursorBrush = SolidColor(White),
                            decorationBox = { innerTextField ->
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (myContactsState.searchQuery.isEmpty() && !isFocused) {
                                        Text(
                                            text = "Search...",
                                            color = White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.W600,
                                            fontFamily = OPENS_SANS
                                        )
                                    }
                                    Box(Modifier.weight(1f)) {
                                        innerTextField()
                                    }
                                }
                            }
                        )
                        IconButton(onClick = {
                            updateState(myContactsState.copy(searchState = false))
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Localized description",
                                tint = White
                            )
                        }
                    }
                }

                false -> {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Contacts",
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
                            IconButton(onClick = {
                                updateState(myContactsState.copy(searchState = true))
                            }) {
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
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            if (myContactsState.isMultiselect) {
                IconButton(
                    onClick = deleteSelected,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Orange)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Icon delete selected contacts",
                        modifier = Modifier.size(60.dp),
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
                    text = "Add contacts",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navigateToAddContacts() },
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
            when (myContactsState.contacts.count { contact ->
                contact.name.lowercase().contains(myContactsState.searchQuery.lowercase())
            } == 0) {
                true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                            .padding(vertical = 32.dp, horizontal = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No results found",
                                color = GrayText,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = OPENS_SANS
                            )
                            Text(
                                text = "You can see more contacts in the recommendation",
                                color = GrayText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = OPENS_SANS
                            )
                        }
                    }
                }

                false -> {
                    ContactsColumn(
                        contacts = myContactsState.contacts.filter { contact ->
                            contact.name.lowercase().contains(
                                myContactsState.searchQuery.lowercase()
                            )
                        },
                        isMultiselect = myContactsState.isMultiselect,
                        deleteContact = deleteContact,
                        changeContactSelectedState = changeContactSelectedState,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }
    }
    if (addContactState.value) {
        DialogAddContact(
            onDismissRequest = { addContactState.value = false },
            onConfirmation = { contact ->
                addContactState.value = false
                addContact(contact)
            }
        )
    }
}

@Composable
private fun ContactsColumn(
    contacts: List<MyContactsContact>,
    isMultiselect: Boolean,
    deleteContact: (Int) -> Unit,
    changeContactSelectedState: (MyContactsContact) -> Unit,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(
            items = contacts,
            key = { _, item -> item }
        ) { index, contact ->
            SwipeableContainer(
                contact = contact,
                isMultiselect = isMultiselect,
                onDelete = deleteContact,
                changeContactSelectedState = changeContactSelectedState,
                navigateToDetail = navigateToDetail,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableContainer(
    contact: MyContactsContact,
    isMultiselect: Boolean,
    onDelete: (Int) -> Unit,
    changeContactSelectedState: (MyContactsContact) -> Unit,
    navigateToDetail: (Int) -> Unit,
    animationDuration: Int = 500,
) {
    var isRemoved by remember { mutableStateOf(false) }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isRemoved) {
        if (isRemoved) {
//            delay(animationDuration.toLong())
            onDelete(contact.id)
            Log.d(
                "AAA",
                "onDelete ItemContact ${contact.name}: ${swipeToDismissBoxState.currentValue.name}"
            )
        }
    }

    AnimatedVisibility(
        visible = isRemoved.not(),
        exit = shrinkVertically(
            animationSpec = tween(animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = swipeToDismissBoxState,
            backgroundContent = {
//                DeleteBackground(swipeDismissState = state)
            },
            content = {
                ItemContact(
                    swipeToDismissBoxState = swipeToDismissBoxState,
                    contact = contact,
                    isMultiselect = isMultiselect,
                    onDelete = onDelete,
                    changeContactSelectedState = changeContactSelectedState,
                    navigateToDetail = navigateToDetail
                )
            },
            enableDismissFromStartToEnd = false,
            enableDismissFromEndToStart = isMultiselect.not()
        )
    }
    LaunchedEffect(isRemoved) {
        if (isRemoved.not()) {
            swipeToDismissBoxState.reset()
            isRemoved = false
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
private fun ItemContact(
    swipeToDismissBoxState: SwipeToDismissBoxState,
    contact: MyContactsContact,
    isMultiselect: Boolean,
    onDelete: (Int) -> Unit,
    changeContactSelectedState: (MyContactsContact) -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gray, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(if (isMultiselect) GrayE7E7E7 else White)
            .combinedClickable(
                onLongClick = {
                    if (isMultiselect.not()) {
                        changeContactSelectedState(contact)
                    }
                },
                onClick = {
                    when (isMultiselect) {
                        true -> {
                            changeContactSelectedState(contact)
                        }

                        false -> {
                            navigateToDetail(contact.id)
                        }
                    }
                }
            )
            .padding(8.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMultiselect) {
            Image(
                painter = painterResource(if (contact.isChecked) R.drawable.circle_checked else R.drawable.circle_gray),
                contentDescription = "multiselect state",
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { changeContactSelectedState(contact) })
            )
        }
        val image =
            if (swipeToDismissBoxState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                R.drawable.black_guy_disappointed
            } else R.drawable.black_guy_happy
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
                text = contact.name,
                color = GrayText,
                fontSize = 18.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS
            )
            Text(
                text = contact.career,
                color = Gray828282,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = OPENS_SANS
            )
        }
        if (isMultiselect.not()) {
            when (contact.updateUiState) {
                true -> {
                    CircularProgressIndicator()
                }

                false -> {
                    IconButton(onClick = { onDelete(contact.id) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Localized description",
                            tint = Orange
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState,
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogAddContact(
    onDismissRequest: () -> Unit = {},
    onConfirmation: (MyContactsContact) -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val nameState = remember { mutableStateOf("") }
        val careerState = remember { mutableStateOf("") }
        val emailState = remember { mutableStateOf("") }
        val phoneState = remember { mutableStateOf("") }
        val addressState = remember { mutableStateOf("") }
        val dateOfBirthState = remember { mutableStateOf("") }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Add contact",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OPENS_SANS,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onDismissRequest() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description",
                                tint = White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Blue,
                        titleContentColor = White
                    )
                )
            }
        ) { innerPaddings ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddings)
            ) {
                Box(
                    Modifier
                        .background(Blue)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.black_guy_happy),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(28.dp)
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    DialogTextField(nameState, "Username")
                    DialogTextField(careerState, "Career")
                    DialogTextField(emailState, "Email")
                    DialogTextField(phoneState, "Phone")
                    DialogTextField(addressState, "Address")
                    DialogTextField(dateOfBirthState, "Date of birth")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = {
                            val contact = MyContactsContact(
                                name = nameState.value,
                                career = careerState.value,
                                address = addressState.value
                            )
                            onConfirmation(contact)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonColors(Orange, Color.Transparent, Orange, Color.Transparent)
                    ) {
                        Text(
                            text = "Save",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OPENS_SANS,
                            letterSpacing = 1.5.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogTextField(state: MutableState<String>, label: String) {
    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, color = Gray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        colors = customTextFieldsColors(
            focusedTextColor = GrayText,
            unfocusedTextColor = GrayText,
            disabledTextColor = GrayText,
            errorTextColor = GrayText,
            cursorColor = GrayText,
            errorCursorColor = GrayText,
            selectionColors = TextSelectionColors(GrayBDBDBD, GrayBDBDBD.copy(0.4f)),
            focusedIndicatorColor = GrayBDBDBD,
            unfocusedIndicatorColor = GrayBDBDBD,
            disabledIndicatorColor = GrayBDBDBD,
            errorIndicatorColor = GrayBDBDBD
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun MyContactsScreenPreview() {
    val contactsProvider = ContactsProvider()
    MyContactsScreen(
        padding = PaddingValues(0.dp),
        updateState = { _ -> },
        snackbarHostState = SnackbarHostState(),
        myContactsState = MyContactsState(
            contacts = contactsProvider.getContacts().map { it.toMyContactsContact() },
            searchState = true
        ),
        addContactState = remember { mutableStateOf(false) },
        deleteContact = {},
        deleteSelected = {},
        addContact = {},
        changeContactSelectedState = {},
        onNavigationArrowClick = {},
        navigateToAddContacts = {},
        navigateToDetail = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DialogAddContactPreview() {
    DialogAddContact()
}
