package com.yuventius.mvi_view_sample.ext

import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ui.view.screen.Screen

fun NavController.route(to: Screen) {
    navigate(to.route)
}

fun NavController.root(to: Screen) {
    popBackStack()
    graph.setStartDestination(to.route)
    navigate(to.route)
}