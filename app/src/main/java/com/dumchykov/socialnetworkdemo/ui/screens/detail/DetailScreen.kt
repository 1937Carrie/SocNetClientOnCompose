package com.dumchykov.socialnetworkdemo.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    parentPadding: PaddingValues,
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val detailState = viewModel.detailState.collectAsState().value

    Column {
        Column(
            modifier = Modifier
                .background(Blue)
                .weight(1f)
                .fillMaxSize()
                .then(
                    Modifier.padding(
                        top = parentPadding.calculateTopPadding(),
                        start = parentPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = parentPadding.calculateEndPadding(LayoutDirection.Ltr)
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                            tint = White
                        )
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White,
                ),
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp, BiasAlignment.Vertical(-0.5f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.image_main),
                    contentDescription = "image main",
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                        .clip(CircleShape)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = detailState.contact.name,
                            color = White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OPENS_SANS,
                        )
                        Text(
                            text = detailState.contact.profession,
                            color = Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = OPENS_SANS
                        )
                    }
                    Text(
                        text = detailState.contact.address,
                        color = Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .background(White)
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp)
                .then(
                    Modifier.padding(
                        bottom = parentPadding.calculateBottomPadding(),
                        start = parentPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = parentPadding.calculateEndPadding(LayoutDirection.Ltr)
                    )
                ),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = BiasAlignment.Vertical(-0.4f)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_facebook),
                    contentDescription = "icon facebook",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                )
                Image(
                    painter = painterResource(R.drawable.ic_linkedin),
                    contentDescription = "icon linkedin",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                )
                Image(
                    painter = painterResource(R.drawable.ic_instagram),
                    contentDescription = "icon instagram",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { }
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(
                    Orange,
                    Blue,
                    Orange,
                    Blue
                ),
            ) {
                Text(
                    text = "Message".uppercase(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                    letterSpacing = 1.5.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        parentPadding = PaddingValues(0.dp),
        navController = rememberNavController()
    )
}
