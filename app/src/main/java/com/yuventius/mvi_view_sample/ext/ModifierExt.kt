package com.yuventius.mvi_view_sample.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutRipple(
    enabled: Boolean = true,
    onClick: () -> Unit
) = composed {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    this
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { onClick.invoke() },
            enabled = enabled
        )
}