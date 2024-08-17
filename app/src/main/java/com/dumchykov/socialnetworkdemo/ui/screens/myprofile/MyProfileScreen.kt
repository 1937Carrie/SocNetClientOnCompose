package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dumchykov.socialnetworkdemo.R
import com.dumchykov.socialnetworkdemo.ui.screens.Pager
import com.dumchykov.socialnetworkdemo.ui.screens.SignUp
import com.dumchykov.socialnetworkdemo.ui.theme.Blue
import com.dumchykov.socialnetworkdemo.ui.theme.Gray
import com.dumchykov.socialnetworkdemo.ui.theme.GrayText
import com.dumchykov.socialnetworkdemo.ui.theme.OPENS_SANS
import com.dumchykov.socialnetworkdemo.ui.theme.Orange
import com.dumchykov.socialnetworkdemo.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun MyProfileScreen(
    padding: PaddingValues,
    navController: NavHostController,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    viewModel: MyProfileViewModel = hiltViewModel(),
) {
    val myProfileState = viewModel.myProfileState.collectAsState().value

    LaunchedEffect(myProfileState.credentialsIsCleared) {
        if (myProfileState.credentialsIsCleared) {
            navController.navigate(SignUp) {
                popUpTo(Pager(myProfileState.argEmail)) {
                    inclusive = true
                }
            }
        }
    }
    BoxWithConstraints {
        if (maxWidth < 500.dp) {
            Column {
                ContainerTop(
                    viewModel,
                    myProfileState,
                    Modifier
                        .background(Blue)
                        .weight(1f)
                        .fillMaxSize()
                        .then(
                            Modifier.padding(
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                end = padding.calculateEndPadding(LayoutDirection.Ltr)
                            )
                        )
                )
                ContainerBottom(
                    pagerState = pagerState,
                    modifier = Modifier
                        .background(White)
                        .weight(1f)
                        .fillMaxSize()
                        .padding(16.dp)
                        .then(
                            Modifier.padding(
                                bottom = padding.calculateBottomPadding(),
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                end = padding.calculateEndPadding(LayoutDirection.Ltr)
                            )
                        )
                )
            }
        } else {
            Row {
                ContainerTop(
                    viewModel,
                    myProfileState,
                    Modifier
                        .background(Blue)
                        .weight(1f)
                        .fillMaxSize()
                        .then(
                            Modifier.padding(
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                end = padding.calculateEndPadding(LayoutDirection.Ltr)
                            )
                        )
                )
                ContainerBottom(
                    pagerState = pagerState,
                    modifier = Modifier
                        .background(White)
                        .weight(1f)
                        .fillMaxSize()
                        .padding(16.dp)
                        .then(
                            Modifier.padding(
                                bottom = padding.calculateBottomPadding(),
                                start = padding.calculateStartPadding(LayoutDirection.Ltr),
                                end = padding.calculateEndPadding(LayoutDirection.Ltr)
                            )
                        )
                )
            }
        }
    }

}

@Composable
private fun ContainerBottom(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
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
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(
                    Color.Transparent,
                    Color.Yellow,
                    Color.Transparent,
                    Color.Yellow
                ),
                border = BorderStroke(2.dp, Blue)
            ) {
                Text(
                    text = "Edit profile",
                    color = GrayText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                )
            }
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(1)
                    }
                },
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
                    text = "View my contacts".uppercase(),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS,
                    letterSpacing = 1.5.sp,
                )
            }
        }
    }
}

@Composable
private fun ContainerTop(
    viewModel: MyProfileViewModel,
    myProfileState: MyProfileState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Settings",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS,
            )
            Text(
                text = "Log out",
                modifier = Modifier
                    .border(2.dp, Gray, RoundedCornerShape(6.dp))
                    .clickable {
                        viewModel.clearCredentials()
                    }
                    .padding(vertical = 10.dp, horizontal = 34.dp),
                color = Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.W600,
                fontFamily = OPENS_SANS,
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
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
                        text = myProfileState.name,
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS,
                    )
                    Text(
                        text = "Graphic designer",
                        color = Gray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W600,
                        fontFamily = OPENS_SANS
                    )
                }
                Text(
                    text = "5295 Gaylord Walks Apk. 110",
                    color = Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = OPENS_SANS
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=landscape"
)
@Composable
private fun MyProfileScreenLandscapePreview() {
    MyProfileScreen(
        PaddingValues(0.dp),
        rememberNavController(),
        rememberPagerState { 2 }
    )
}

@Preview(showBackground = true)
@Composable
private fun MyProfileScreenPreview() {
    MyProfileScreen(
        PaddingValues(0.dp),
        rememberNavController(),
        rememberPagerState { 2 }
    )
}
