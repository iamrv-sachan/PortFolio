package com.example.protfolio.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.geometry.Offset
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

/**
 * A scroll-driven reveal effect that scales and translates an item based on its visibility 
 * in a LazyList.
 * 
 * @param listState The state of the LazyList to track scroll position.
 * @param index The index of the item within the LazyList.
 * @param slideDistance The vertical distance the item slides up from.
 */
fun Modifier.scrollDrivenReveal(
    listState: LazyListState,
    index: Int,
    slideDistance: Dp = 40.dp,
    enabled: Boolean = true
): Modifier = if (enabled.not()) this else this.graphicsLayer {
    val layoutInfo = listState.layoutInfo
    val itemInfo = layoutInfo.visibleItemsInfo.find { it.index == index }
    
    if (itemInfo != null) {
        // Calculate how much of the item is visible from the bottom
        val itemStart = itemInfo.offset
        val itemSize = itemInfo.size
        val viewportEnd = layoutInfo.viewportEndOffset
        
        // Progress goes from 0 (just starting to enter from bottom) to 1 (fully visible)
        val visibleAmount = (viewportEnd - itemStart).toFloat()
        val progress = (visibleAmount / itemSize).coerceIn(0f, 1f)
        
        // Apply transformations
        this.alpha = 0.3f + (0.7f * progress)
        this.translationY = slideDistance.toPx() * (1f - progress)
        this.scaleX = 1.15f - (0.15f * progress)
        this.scaleY = 1.05f - (0.05f * progress)
        this.shadowElevation = 12f * (1f - progress)
    } else {
        // If not in visibleItemsInfo, it's either above or below.
        val firstVisible = layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
        if (firstVisible > index) {
            // Item is above viewport - show normally
            this.alpha = 1f
            this.translationY = 0f
            this.scaleX = 1f
            this.scaleY = 1f
            this.shadowElevation = 0f
        } else {
            // Item is below viewport - start hidden state
            this.alpha = 0.3f
            this.translationY = slideDistance.toPx()
            this.scaleX = 1.15f
            this.scaleY = 1.05f
            this.shadowElevation = 12f
        }
    }
}

/**
 * An interactive 3D tilt effect that responds to pointer position.
 * The item tilts and changes elevation towards the cursor.
 */
fun Modifier.interactiveTilt(
    maxRotationX: Float = 10f,
    maxRotationY: Float = 10f,
    targetElevation: Float = 8f,
    enabled: Boolean = true
): Modifier = if (enabled.not()) this else this.composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var pointerOffset by remember { mutableStateOf<Offset?>(null) }

    val rotX by animateFloatAsState(
        targetValue = pointerOffset?.let {
            val centerY = size.height / 2f
            ((it.y - centerY) / centerY).coerceIn(-1f, 1f) * -maxRotationX
        } ?: 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    val rotY by animateFloatAsState(
        targetValue = pointerOffset?.let {
            val centerX = size.width / 2f
            ((it.x - centerX) / centerX).coerceIn(-1f, 1f) * maxRotationY
        } ?: 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    val elevation by animateFloatAsState(
        targetValue = if (pointerOffset != null) targetElevation else 0f,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    )

    this.onSizeChanged { size = it }
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    when (event.type) {
                        PointerEventType.Move, PointerEventType.Enter -> {
                            pointerOffset = event.changes.first().position
                        }
                        PointerEventType.Exit -> {
                            pointerOffset = null
                        }
                    }
                }
            }
        }
        .graphicsLayer {
            rotationX = rotX
            rotationY = rotY
            shadowElevation = elevation
            cameraDistance = 8f * density
        }
}

