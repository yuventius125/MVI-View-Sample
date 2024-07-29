package com.yuventius.mvi_view_sample.ui.view.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ui.view.base.UiState
import com.yuventius.mvi_view_sample.ui.view.screen.home.component.SpaceXHistoryItemView

@Composable
fun HomeView (
    navController: NavController,
    vm: HomeVM = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (uiState.value) {
            UiState.Failed -> Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "UNKNOWN ERROR"
            )
            UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                    vm.onEvent(HomeEvent.GetRemoteHistories)
                }
            }
            is UiState.Loaded -> {
                val data = (uiState.value as UiState.Loaded).data
                LazyColumn (
                    state = listState,
                    userScrollEnabled = true,
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(data.remoteHistories) { item ->
                        SpaceXHistoryItemView(
                            modifier = Modifier,
                            color = Color.White,
                            historyItem = item,
                            onFavoriteClick = { id, isOn ->

                            },
                            onClick = { id ->

                            }
                        )
                    }
                }
            }
        }
    }
}