package com.example.gharprcustomer.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gharprcustomer.R
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.theme.Black1
import com.example.gharprcustomer.ui.theme.LightBlack
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetStartedPagerScreen(
    modifier: Modifier = Modifier, 
    navController: NavController,
    splashViewModel: SplashScreenViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    // Screen dimensions
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    // Page Animations
    val pageAnimations = List(3) { 
        remember { 
            PageAnimations(
                scale = Animatable(0.8f),
                alpha = Animatable(0f)
            ) 
        }
    }

    // Animate current page
    LaunchedEffect(pagerState.currentPage) {
        pageAnimations.forEachIndexed { index, animations ->
            if (index == pagerState.currentPage) {
                launch {
                    animations.scale.animateTo(
                        targetValue = 1f, 
                        animationSpec = tween(
                            durationMillis = 500, 
                            easing = FastOutSlowInEasing
                        )
                    )
                }
                launch {
                    animations.alpha.animateTo(
                        targetValue = 1f, 
                        animationSpec = tween(durationMillis = 500)
                    )
                }
            } else {
                animations.scale.snapTo(0.8f)
                animations.alpha.snapTo(0f)
            }
        }
    }

    val pages = listOf(
        PageData(
            title = "Choose a Service",
            description = "Explore a wide range of services tailored to your needs, with just a few taps.",
            imageRes = R.drawable.get_started_pager1_image_2x
        ),
        PageData(
            title = "Book Appointment",
            description = "Schedule services at your convenience, giving you complete control over your time.",
            imageRes = R.drawable.get_started_pager2_image_2x
        ),
        PageData(
            title = "Expert Service",
            description = "Sit back and relax while our skilled professionals deliver top-quality results.",
            imageRes = R.drawable.get_started_pager3_image_2x
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
            .padding(horizontal = screenWidth * 0.05f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState, 
            modifier = Modifier.weight(1f)
        ) { page ->
            GetStartedPagerDesign(
                title = pages[page].title,
                description = pages[page].description,
                imageRes = pages[page].imageRes,
                scale = pageAnimations[page].scale.value,
                alpha = pageAnimations[page].alpha.value
            )
        }

        // Page Indicator
        Row(
            modifier = Modifier.padding(vertical = screenHeight * 0.04f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .background(
                            color = if (pagerState.currentPage == index) Orange else Color.LightGray, 
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }

        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = screenHeight * 0.04f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Skip Button
            TextButton(
                onClick = {
                    splashViewModel.completeOnboarding()
                    navController.navigate(Screen.AuthOption.route) {
                        popUpTo(Screen.GetStartedPager.route) { inclusive = true }
                    }
//                    navController.navigate("auth_option")
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.defaultMinSize(
                    minHeight = screenHeight * 0.055f,
                    minWidth = screenWidth * 0.3f
                )
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close, 
                    contentDescription = "Skip",
                    tint = LightBlack
                )
                Text(
                    text = "Skip",
                    color = LightBlack,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Next/Finish Button
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pagerState.pageCount - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            splashViewModel.completeOnboarding()
                            navController.navigate(Screen.AuthOption.route) {
                                popUpTo(Screen.GetStartedPager.route) { inclusive = true }
                            }
//                            navController.navigate("auth_option")
                        }
                    }
                },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    contentColor = White1
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.defaultMinSize(
                    minHeight = screenHeight * 0.055f,
                    minWidth = screenWidth * 0.3f
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == pagerState.pageCount - 1) "Finish" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowForward, 
                    contentDescription = "Next",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun GetStartedPagerDesign(
    title: String, 
    description: String, 
    imageRes: Int,
    scale: Float = 1f,
    alpha: Float = 1f
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = screenWidth * 0.05f)
            .scale(scale)
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .aspectRatio(1f)
        )

        Spacer(modifier = Modifier.height(screenWidth * 0.04f))

        Text(
            text = title,
            fontSize = (screenWidth * 0.06f).value.sp,
            lineHeight = (screenWidth * 0.08f).value.sp,
            textAlign = TextAlign.Center,
            color = Black1,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = screenWidth * 0.04f)
        )

        Spacer(modifier = Modifier.height(screenWidth * 0.02f))

        Text(
            text = description,
            fontSize = (screenWidth * 0.04f).value.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = LightBlack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = screenWidth * 0.04f)
        )
    }
}

// Helper class for page animations
data class PageAnimations(
    val scale: Animatable<Float, *>,
    val alpha: Animatable<Float, *>
)

data class PageData(val title: String, val description: String, val imageRes: Int)

@Preview
@Composable
fun GetStartedPagerScreenPreview() {
    GetStartedPagerScreen(navController = rememberNavController())
}