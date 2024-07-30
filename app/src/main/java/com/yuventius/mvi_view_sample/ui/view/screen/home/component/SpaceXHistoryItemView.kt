package com.yuventius.mvi_view_sample.ui.view.screen.home.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuventius.domain.model.HistoryEvent
import com.yuventius.mvi_view_sample.ext.toFormattedString
import com.yuventius.mvi_view_sample.ext.toLocalDateTimeByUTC

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SpaceXHistoryItemView(
    modifier: Modifier = Modifier,
    historyItem: HistoryEvent,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    elevation: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    isFavoriteEnabled: Boolean = false,
    onFavoriteClick: (String, Boolean) -> Unit,
    onClick: (String) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .clickable {
                onClick(historyItem.id)
            },
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation
        )
    ) {
        with(sharedTransitionScope) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(
                                    key = "title_${historyItem.id}"
                                ),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .weight(1f)
                            .padding(PaddingValues(end = 12.dp, top = 12.dp, bottom = 12.dp)),
                        text = historyItem.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "favorite_${historyItem.id}"),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .size(20.dp)
                            .clickable {
                                onFavoriteClick(historyItem.id, isFavoriteEnabled.not())
                            },
                        imageVector = if (isFavoriteEnabled) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState(key = "details_${historyItem.id}"),
                            animatedVisibilityScope = animatedContentScope
                        ),
                    text = historyItem.details,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "date_${historyItem.id}"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                        text = historyItem.eventDateUtc.toLocalDateTimeByUTC()?.toFormattedString() ?: "",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )

                }
            }
        }
    }
}