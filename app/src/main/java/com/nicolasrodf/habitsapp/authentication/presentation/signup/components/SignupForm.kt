package com.nicolasrodf.habitsapp.authentication.presentation.signup.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nicolasrodf.habitsapp.authentication.presentation.signup.SignupEvent
import com.nicolasrodf.habitsapp.authentication.presentation.signup.SignupState
import com.nicolasrodf.habitsapp.core.presentation.HabitButton
import com.nicolasrodf.habitsapp.core.presentation.HabitEmailTextfield
import com.nicolasrodf.habitsapp.core.presentation.HabitPasswordTextfield

@Composable
fun SignupForm(
    state: SignupState,
    onEvent: (SignupEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        HabitEmailTextfield(
            value = state.email,
            onValueChange = { onEvent(SignupEvent.EmailChange(it)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp).padding(horizontal = 20.dp),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.moveFocus(FocusDirection.Next)
            }),
            errorMessage = state.emailError,
            isEnabled = !state.isLoading,
            backgroundColor = Color.White
        )
        HabitPasswordTextfield(
            value = state.password,
            onValueChange = { onEvent(SignupEvent.PasswordChange(it)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp).padding(horizontal = 20.dp),
            errorMessage = state.passwordError,
            isEnabled = !state.isLoading,
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
                onEvent(SignupEvent.SignUp)
            }),
            backgroundColor = Color.White
        )
        Spacer(modifier = Modifier.height(12.dp))
        HabitButton(
            text = "Create Account",
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            isEnabled = !state.isLoading
        ) {
            onEvent(SignupEvent.SignUp)
        }
        TextButton(onClick = { onEvent(SignupEvent.LogIn) }) {
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Sign in")
                    }
                },
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}