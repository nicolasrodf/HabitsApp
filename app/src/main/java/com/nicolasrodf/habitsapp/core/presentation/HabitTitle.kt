package com.nicolasrodf.habitsapp.core.presentation

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HabitTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.tertiary
        ),
        textAlign = TextAlign.Center
    )
}