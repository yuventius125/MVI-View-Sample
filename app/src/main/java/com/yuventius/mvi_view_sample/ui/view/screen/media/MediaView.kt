package com.yuventius.mvi_view_sample.ui.view.screen.media

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavController
import com.yuventius.mvi_view_sample.ext.clickableWithoutRipple
import com.yuventius.mvi_view_sample.ext.getActivity
import com.yuventius.mvi_view_sample.ext.getMediaFileDuration
import com.yuventius.mvi_view_sample.ext.toTimeStamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaView(
    navController: NavController,
    vm: MediaVM = hiltViewModel()
) {
    val context = LocalContext.current
    val state = vm.state.collectAsState()
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                modifier = Modifier
                    .weight(1f)
            ) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp)
                ) {
                    itemsIndexed(state.value.recordList) { index, item ->
                        if (index != 0) {
                            HorizontalDivider(
                                color = Color.Black,
                                thickness = 1.dp
                            )
                        }
                        Row(
                            modifier = Modifier
                                .animateItem()
                                .fillMaxSize()
                                .clickable {
                                    vm.onEvent(MediaEvent.StartPlay(item))
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(6f),
                                text = item,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = item.getMediaFileDuration(context).toTimeStamp(),
                                fontSize = 12.sp,
                                textAlign = TextAlign.End
                            )
                        }
                        if (state.value.recordList.lastIndex != index) {
                            HorizontalDivider(
                                color = Color.Black,
                                thickness = 1.dp
                            )
                        }
                    }
                }
                if (state.value.isRecording) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f))
                            .clickableWithoutRipple { }
                    )
                }
            }
            Row {
                if (state.value.isPlaying || state.value.isSliderDraggingState) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Slider(
                            value = state.value.currentPosition.toFloat(),
                            onValueChange = {
                                Log.d("[Slider]", "value changed: ${it}")
                                vm.onEvent(MediaEvent.SeekTo(it))
                            },
                            onValueChangeFinished = {
                                Log.d("[Slider]", "value changed finished")
                                vm.onEvent(MediaEvent.Seek)
                            },
                            valueRange = 0F .. state.value.totalDuration.toFloat(),
                            interactionSource = interactionSource,
                            thumb = {
                                Icon(imageVector = Icons.Default.Face, contentDescription = "")
                            }
                        )
//                        LinearProgressIndicator(progress = { state.value.currentPosition.toFloat() / state.value.totalDuration })
                        Row {
                            Text(text = "${state.value.currentPosition.toTimeStamp()} / ${state.value.totalDuration.toTimeStamp()}")
                        }
                        IconButton(onClick = { vm.onEvent(MediaEvent.StopPlay) }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        }
                    }
                } else {
                    if (state.value.isRecording) {
                        IconButton(onClick = { vm.onEvent(MediaEvent.StopRecord) }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                        }
                    } else {
                        IconButton(onClick = { vm.onEvent(MediaEvent.StartRecord) }) {
                            Icon(imageVector = Icons.Default.AddCircle, contentDescription = "")
                        }
                    }
                }
            }
        }
        
        LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
            vm.onEvent(MediaEvent.Initialize(context.getActivity() ?: return@LifecycleEventEffect))
        }
    }
}