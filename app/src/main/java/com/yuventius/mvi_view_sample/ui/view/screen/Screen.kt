package com.yuventius.mvi_view_sample.ui.view.screen

sealed class Screen(val route: String) {
    sealed class Root {
        data object Splash : Screen("splash") {
            const val ROOT: String = "SplashRoot"
        }
        data object Login: Screen("login") {
            const val ROOT: String = "LoginRoot"
        }
        data object Home: Screen("home") {
            const val ROOT: String = "HomeRoot"
        }
    }
    sealed class Route {
        data object HistoryDetail: Screen("history_detail")
    }
}