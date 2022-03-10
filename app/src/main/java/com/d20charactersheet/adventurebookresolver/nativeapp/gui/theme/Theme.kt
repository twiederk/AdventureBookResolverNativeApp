package com.d20charactersheet.adventurebookresolver.nativeapp.gui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AdventureBookResolverTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        content = content
    )
}

private val LightColors = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryDarkColor,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryColor,
    secondaryVariant = SecondaryDarkColor,
    onSecondary = SecondaryTextColor,
    error = Color.Red
)
