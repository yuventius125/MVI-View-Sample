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
        data object QR: Screen("qr") {
            const val ROOT: String = "QRRoot"
        }
        data object Record: Screen("record") {
            const val ROOT: String = "RecordRoot"
        }
    }
    sealed class Route {
        data object HistoryDetail: Screen("history_detail?historyItem={historyItem}")
        data object QRImage: Screen("qr_image/{qrString}")
        data object QRCamera: Screen("qr_camera")
    }
}