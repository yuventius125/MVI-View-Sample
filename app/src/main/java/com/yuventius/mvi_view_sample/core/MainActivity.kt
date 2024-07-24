package com.yuventius.mvi_view_sample.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.yuventius.mvi_view_sample.ui.theme.MVIViewSampleTheme
import com.yuventius.mvi_view_sample.ui.view.component.CustomAppBar
import com.yuventius.mvi_view_sample.ui.view.component.CustomAppBarState
import com.yuventius.mvi_view_sample.ui.view.screen.RootView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVIViewSampleTheme {
                val navController = rememberNavController()
                var customAppBarState= remember<CustomAppBarState?> {
                    null
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        customAppBarState?.let {
                            CustomAppBar(it)
                        }
                    }
                ) { innerPadding ->
                    RootView(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        changeAppBarState = {
                            customAppBarState = it
                        }
                    )
                }
            }
        }
    }
}