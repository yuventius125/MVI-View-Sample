package com.yuventius.mvi_view_sample.ui.view.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.yuventius.mvi_view_sample.ext.route
import com.yuventius.mvi_view_sample.ui.view.component.CustomAppBarState
import com.yuventius.mvi_view_sample.ui.view.screen.home.HomeView
import com.yuventius.mvi_view_sample.ui.view.screen.login.LoginView
import com.yuventius.mvi_view_sample.ui.view.screen.splash.SplashView

@Composable
fun RootView(
    modifier: Modifier,
    navController: NavHostController,
    changeAppBarState: (CustomAppBarState?) -> Unit,
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Root.Splash.ROOT
    ) {
        navigation(route = Screen.Root.Splash.ROOT, startDestination = Screen.Root.Splash.route) {
            composable(Screen.Root.Splash.route) {
                SplashView(navController = navController)
                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                    changeAppBarState(null)
                }
            }
        }
        navigation(route = Screen.Root.Login.ROOT, startDestination = Screen.Root.Login.route) {
            composable(Screen.Root.Login.route) {
                LoginView(navController = navController)
                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                    changeAppBarState(null)
                }
            }
        }
        navigation(route = Screen.Root.Home.ROOT, startDestination = Screen.Root.Home.route) {
            composable(Screen.Root.Home.route) {
                HomeView(navController = navController)
                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                    changeAppBarState(null)
                }
            }
            composable(Screen.Route.HistoryDetail.route) {
                Text(text = "HistoryDetail")
                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                    changeAppBarState(CustomAppBarState(title = "HistoryDetail", onBack = { navController.navigateUp() }, onAction = {}))
                }
            }
        }
    }
}