package com.yuventius.mvi_view_sample.ui.view.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yuventius.mvi_view_sample.R
import com.yuventius.mvi_view_sample.ui.theme.CustomTextFieldColors

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    val iconSize = 18.dp

    OutlinedTextField(
        modifier = modifier,
        colors = CustomTextFieldColors,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = it,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            trailingIcon?.let {
                IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = trailingIcon,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val iconSize = 18.dp

    val isPassword = keyboardType == KeyboardType.Password
    val passwordShown = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        colors = CustomTextFieldColors,
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = it,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (isPassword) {

                IconButton(onClick = { passwordShown.value = !passwordShown.value }) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        painter = if (passwordShown.value)
                            painterResource(id = R.drawable.ic_visibility)
                        else
                            painterResource(id = R.drawable.ic_visibility_off),
                        contentDescription = null
                    )
                }
            } else {
                trailingIcon?.let {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(
                            modifier = Modifier.size(iconSize),
                            imageVector = trailingIcon,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        visualTransformation = if (isPassword && passwordShown.value.not()) PasswordVisualTransformation() else VisualTransformation.None
    )
}