package com.pearl.team.switchtheme

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(
    darkTheme: Boolean = false,
    size: Int = 64,
    iconSize: Int = size / 3,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    leftIconId : Int,
    rightIconId : Int,
    secondaryContainerColor : Color,
    primaryColor : Color,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: (Offset) -> Unit
) {
    val animatedOffset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size.dp,
        animationSpec = animationSpec, label = ""
    )

    var toggleOffset: Offset = remember { Offset(0f, 0f) }

    Box(modifier = Modifier.onGloballyPositioned {
        toggleOffset = Offset(
            x = it.positionInWindow().x + it.size.width / 2,
            y = it.positionInWindow().y + it.size.height / 2
        )
    }
        .width(size.dp * 2)
        .height(size.dp)
        .clip(shape = parentShape)
        .clickable { onClick(toggleOffset) }
        .background(secondaryContainerColor)
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .offset(x = animatedOffset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(primaryColor)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = primaryColor
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize.dp),
                    painter = painterResource(id = leftIconId),
                    contentDescription = "Theme Icon",
                    tint = if (darkTheme) secondaryContainerColor
                    else primaryColor
                )
            }
            Box(
                modifier = Modifier.size(size.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize.dp),
                    painter = painterResource(id = rightIconId),
                    contentDescription = "Theme Icon",
                    tint = if (darkTheme) primaryColor
                    else secondaryContainerColor
                )
            }
        }
    }
}