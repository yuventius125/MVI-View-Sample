package com.yuventius.mvi_view_sample.ui.view.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.yuventius.mvi_view_sample.ui.view.component.CustomAppBarState
import com.yuventius.mvi_view_sample.ui.view.screen.home.HomeView
import com.yuventius.mvi_view_sample.ui.view.screen.home.detail.HomeDetailView
import com.yuventius.mvi_view_sample.ui.view.screen.login.LoginView
import com.yuventius.mvi_view_sample.ui.view.screen.media.MediaView
import com.yuventius.mvi_view_sample.ui.view.screen.qr.QRView
import com.yuventius.mvi_view_sample.ui.view.screen.qr.camera.QRCameraView
import com.yuventius.mvi_view_sample.ui.view.screen.qr.image.QRImageView
import com.yuventius.mvi_view_sample.ui.view.screen.splash.SplashView
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RootView(
    modifier: Modifier,
    navController: NavHostController,
    changeAppBarState: (CustomAppBarState?) -> Unit,
) {
    SharedTransitionLayout (
        modifier = modifier
            .fillMaxSize()
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.Root.Splash.ROOT
        ) {
            navigation(route = Screen.Root.Splash.ROOT, startDestination = Screen.Root.Splash.route) {
                composable(
                    Screen.Root.Splash.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    SplashView(navController = navController)
                    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                        changeAppBarState(null)
                    }
                }
            }
            navigation(route = Screen.Root.Login.ROOT, startDestination = Screen.Root.Login.route) {
                composable(
                    Screen.Root.Login.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    LoginView(navController = navController)
                    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                        changeAppBarState(null)
                    }
                }
            }
            navigation(route = Screen.Root.Home.ROOT, startDestination = Screen.Root.Home.route) {
                composable(
                    Screen.Root.Home.route,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    HomeView(
                        navController = navController,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    ) {
                        changeAppBarState(null)
                    }
                }
                composable(
                    Screen.Route.HistoryDetail.route,
                    arguments = listOf(
                        navArgument("historyItem") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) {
                    HomeDetailView(
                        navController = navController,
                        historyEvent = Json.decodeFromString(it.arguments?.getString("historyItem") ?: ""),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable
                    )
                    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                        changeAppBarState(CustomAppBarState(title = "HistoryDetail", onBack = { navController.navigateUp() }, onAction = {}))
                    }
                }
            }
            navigation(route = Screen.Root.QR.ROOT, startDestination = Screen.Root.QR.route) {
                composable(Screen.Root.QR.route) {
                    QRView(navController = navController) {
                        changeAppBarState(null)
                    }
                }
                composable(
                    Screen.Route.QRImage.route,
                    arguments = listOf(
                        navArgument("qrString") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) {
                    QRImageView(navController = navController, qrString = it.arguments?.getString("qrString") ?: "") {
                        changeAppBarState(CustomAppBarState(title = "QRImage", onBack = { navController.navigateUp() }, onAction = {}))
                    }
                }
                composable(Screen.Route.QRCamera.route) {
                    QRCameraView(navController = navController) {
                        changeAppBarState(CustomAppBarState(title = "QRCamera", onBack = { navController.navigateUp() }, onAction = {}))
                    }
                }
            }
            navigation(route = Screen.Root.Record.ROOT, startDestination = Screen.Root.Record.route) {
                composable(
                    Screen.Root.Record.route
                ) {
                    MediaView(navController = navController)
                }
            }
        }
    }
}