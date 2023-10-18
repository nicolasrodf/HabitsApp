package com.nicolasrodf.habitsapp.core.presentation

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun HabitTitle(
    title: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 28.sp
) {
    Text(
        text = title.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = fontSize
        ),
        textAlign = TextAlign.Center
    )
}