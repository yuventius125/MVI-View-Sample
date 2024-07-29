package com.yuventius.mvi_view_sample.ext

import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ui.view.screen.Screen

fun NavController.route(to: Screen) {
    navigate(to.route)
}

fun NavController.route(to: Screen, args: Map<String, String>) {
    var route = to.route
    for ((key, value) in args) {
        route = route.replace("{$key}", value)
    }
    navigate(route)
}

fun NavController.root(to: Screen) {
    popBackStack()
    graph.setStartDestination(to.route)
    navigate(to.route)
}