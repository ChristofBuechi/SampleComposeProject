package com.medium

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


private val background = Color(0xff_000000)
private val primary = Color(0xff_7F52FF)
private val secondary = Color(0xff_E24462)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FollowingArrows() {
    var mousePosition by remember { mutableStateOf(Offset.Zero) }

    var width by remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(0f) }

    Column(
        Modifier
            .onSizeChanged {
                width = it.width.toFloat()
                height = it.height.toFloat()
            }
            .fillMaxSize()
            .background(background)
            .onPointerEvent(PointerEventType.Move) {
                mousePosition = it.changes.first().position
            }
    ) {
        for (i in 0..10) {
            Row(Modifier.fillMaxSize().weight(1f)) {
                for (j in 0..10) {
                    var layoutCoordinates: LayoutCoordinates? by remember {
                        mutableStateOf(null)
                    }

                    var rotation: Float by remember {
                        mutableStateOf(0f)
                    }

                    var scale: Float by remember {
                        mutableStateOf(1f)
                    }

                    var offset by remember {
                        mutableStateOf(Offset.Zero)
                    }

                    LaunchedEffect(layoutCoordinates, mousePosition) {
                        layoutCoordinates?.let {
                            val center = it.boundsInWindow().center
                            val delta = center - mousePosition

                            val angle = (atan2(delta.y, delta.x) * 180 / PI).toFloat()

                            rotation += ((((angle - rotation) % 360f) + 540f) % 360f) - 180f

                            val diagonal = sqrt(width.pow(2) + height.pow(2))
                            val distance = sqrt(delta.x.pow(2) + delta.y.pow(2))

                            val displacement = 100f

                            offset = Offset(
                                displacement * (delta.x / distance),
                                displacement * (delta.y / distance),
                            )

                            scale = max(1f - (distance / (diagonal * .9f)) * 1f, .4f)
                        } ?: 0f
                    }


                    val animatedRotation by animateFloatAsState(
                        targetValue = rotation,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                        )
                    )

                    val animatedScale by animateFloatAsState(
                        targetValue = scale,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                            dampingRatio = Spring.DampingRatioLowBouncy,
                        )
                    )

                    val animatedOffset by animateOffsetAsState(
                        targetValue = offset,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessVeryLow,
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                        )
                    )

                    Box(
                        Modifier
                            .onGloballyPositioned {
                                layoutCoordinates = it
                            }
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center,
                    ) {

                        Image(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .offset { animatedOffset.toIntOffset() }
                                .rotate(animatedRotation)
                                .scale(animatedScale)
                                .fillMaxSize(),
                            colorFilter = ColorFilter.tint(
                                lerp(primary, secondary, 1f - scale)
                            )
                        )
                    }
                }
            }
        }
    }
}

fun Offset.toIntOffset(): IntOffset {
    return IntOffset(x.roundToInt(), y.roundToInt())
}