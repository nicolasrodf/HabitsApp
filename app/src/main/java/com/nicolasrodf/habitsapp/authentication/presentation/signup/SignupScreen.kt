package com.nicolasrodf.habitsapp.authentication.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolasrodf.habitsapp.R
import com.nicolasrodf.habitsapp.authentication.presentation.signup.components.SignupForm
import com.nicolasrodf.habitsapp.core.presentation.HabitTitle

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(painter = painterResource(id = R.drawable.signup), contentDescription = null)
        HabitTitle(title = "Create your account", fontSize = 20.sp)
        SignupForm(state, viewModel::onEvent, modifier = Modifier.fillMaxWidth())
    }
}