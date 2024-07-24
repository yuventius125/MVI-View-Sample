package com.yuventius.mvi_view_sample.ui.view.base

interface Copyable<out T> {
    fun copy(): T
}