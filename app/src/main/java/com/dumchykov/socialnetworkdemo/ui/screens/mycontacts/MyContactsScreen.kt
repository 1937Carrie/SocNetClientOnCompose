package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dumchykov.contactsprovider.domain.Contact
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyContactsScreen(
    padding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: MyContactsViewModel = viewModel(factory = MyContactsViewModel.factory())
    val myContactsState = viewModel.myContactsState.collectAsState().value
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue)
            .padding(
                bottom = padding.calculateBottomPadding(),
                start = padding.calculateStartPadding(LayoutDirection.Rtl),
                end = padding.calculateEndPadding(LayoutDirection.Rtl)
            ),
        topBar = {
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
                    IconButton(onClick = { }) {
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                ),
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
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
                        .clickable { },
                    color = White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
            ContactsColumn(myContactsState.contacts, viewModel, scope, snackbarHostState)
        }
    }
}

@Composable
private fun ContactsColumn(
    contacts: List<Contact>,
    viewModel: MyContactsViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(contacts, { _, item -> item }) { index, contact ->
            SwipeableContainer(
                contact = contact,
                onDelete = {
                    viewModel.deleteContact(it)
                    scope.launch {
                        // Check if a snackbar is currently being displayed
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState
                            .showSnackbar(
                                message = "${contact.name} has been deleted",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Long
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                viewModel.addContact(index, contact)

                            }

                            SnackbarResult.Dismissed -> {
                                /* Handle snackbar dismissed */
                            }
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableContainer(
    contact: Contact,
    onDelete: (Contact) -> Unit,
    animationDuration: Int = 500,
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
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
            onDelete(contact)
            Log.d("AAA", "onDelete ItemContact ${contact.name}: ${state.currentValue.name}")
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
            state = state,
            backgroundContent = {
//                DeleteBackground(swipeDismissState = state)
            },
            content = {
                ItemContact(state, contact, onDelete)
            },
            enableDismissFromStartToEnd = false
        )
    }
    LaunchedEffect(isRemoved) {
        if (isRemoved.not()) {
            state.reset()
            Log.d("AAA", "onRestore ItemContact ${contact.name}: ${state.currentValue.name}")
            isRemoved = false
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ItemContact(
    state: SwipeToDismissBoxState,
    contact: Contact,
    onDelete: (Contact) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Gray, RoundedCornerShape(6.dp))
            .padding(8.dp)
            .height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val image = if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
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
                text = contact.profession,
                color = Gray828282,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = OPENS_SANS
            )
        }
        IconButton(onClick = { onDelete(contact) }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Localized description",
                tint = Orange
            )
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

@Preview(showBackground = true)
@Composable
fun MyContactsScreenPreview() {
    MyContactsScreen(padding = PaddingValues(0.dp), navController = rememberNavController())
}