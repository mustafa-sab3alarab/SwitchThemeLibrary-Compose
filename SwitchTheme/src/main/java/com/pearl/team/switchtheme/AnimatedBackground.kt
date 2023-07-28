package com.pearl.team.switchtheme

import android.text.style.BackgroundColorSpan
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset

@Composable
fun AnimatedBackground(
    darkTheme: Boolean = false,
    startAnimationPosition : IntOffset,
    backgroundColor : Color,
    content: @Composable () -> Unit
) {

    AnimatedContent(
        transitionSpec = {
            fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(100)
            ) togetherWith fadeOut(
                targetAlpha = .9f,
                animationSpec = tween(800)
            ) + scaleOut(
                targetScale = 1f,
                animationSpec = tween(800)
            )
        },
        targetState = darkTheme,
        modifier = Modifier.background(Color.Black).fillMaxSize(),
        label = "",
    ) { currentTheme ->
        val revealSize = remember { Animatable(1f) }
        LaunchedEffect(key1 = "reveal", block = {
            if (startAnimationPosition.x > 0f) {
                revealSize.snapTo(0f)
                revealSize.animateTo(1f, animationSpec = tween(800))
            } else {
                revealSize.snapTo(1f)
            }
        })
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CirclePath(revealSize.value, startAnimationPosition.toOffset()))
        ) {

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = backgroundColor
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    content()
                }
            }
        }
        currentTheme
    }
}