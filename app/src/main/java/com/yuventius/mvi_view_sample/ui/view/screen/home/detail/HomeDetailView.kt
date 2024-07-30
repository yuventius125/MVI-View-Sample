package com.yuventius.mvi_view_sample.ui.view.screen.home.detail

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.mvi_view_sample.ext.toFormattedString
import com.yuventius.mvi_view_sample.ext.toLocalDateTimeByUTC
import com.yuventius.mvi_view_sample.ui.view.base.UiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeDetailView (
    navController: NavController,
    historyEvent: HistoryEvent,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    vm: HomeDetailVM = hiltViewModel(
        creationCallback = { factory: HomeDetailVM.HomeDetailVMFactory ->
            factory.create(historyEvent)
        }
    )
) {
    val context = LocalContext.current
    val uiState = vm.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        with(sharedTransitionScope) {
            when (uiState.value) {
                is UiState.Failed -> Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "UNKNOWN ERROR"
                )
                is UiState.Loading -> CircularProgressIndicator()
                is UiState.Loaded -> {
                    val data = (uiState.value as UiState.Loaded).data

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "title_${data.historyEvent.id}"),
                                        animatedVisibilityScope = animatedContentScope
                                    )
                                    .weight(1f),
                                text = data.historyEvent.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Icon(
                                modifier = Modifier
                                    .sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "favorite_${data.historyEvent.id}"),
                                        animatedVisibilityScope = animatedContentScope
                                    )
                                    .size(20.dp)
                                    .clickable {
                                        if (data.favorites.contains(data.historyEvent)) {
                                            vm.onEvent(HomeDetailEvent.DeleteFavorite(data.historyEvent))
                                        } else {
                                            vm.onEvent(HomeDetailEvent.InsertFavorite(data.historyEvent))
                                        }
                                    },
                                imageVector = if (data.favorites.contains(data.historyEvent)) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = ""
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1F))
                            Text(
                                modifier = Modifier
                                    .sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "date_${data.historyEvent.id}"),
                                        animatedVisibilityScope = animatedContentScope
                                    ),
                                text = data.historyEvent.eventDateUtc.toLocalDateTimeByUTC()?.toFormattedString() ?: ""
                            )
                        }
                        Text(
                            modifier = Modifier
                                .sharedElement(
                                    sharedTransitionScope.rememberSharedContentState(key = "details_${data.historyEvent.id}"),
                                    animatedVisibilityScope = animatedContentScope
                                )
                                .padding(12.dp),
                            text = data.historyEvent.details
                        )
                    }

                    LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
                        vm.onEvent(HomeDetailEvent.GetFavorites)
                    }
                }
            }
        }
    }
}