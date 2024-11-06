package com.nicolasrodf.habitsapp.authentication.presentation.login

import com.nicolasrf.authentication_presentation.login.LoginEvent
import com.nicolasrodf.habitsapp.authentication.data.repository.FakeAuthenticationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var loginViewModel: com.nicolasrf.authentication_presentation.login.LoginViewModel
    private lateinit var authenticationRepository: FakeAuthenticationRepository

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        authenticationRepository = FakeAuthenticationRepository()
        val usecases = com.nicolasrf.authentication_domain.usecase.LoginUseCases(
            loginWithEmailUseCase = com.nicolasrf.authentication_domain.usecase.LoginWithEmailUseCase(
                authenticationRepository
            ),
            validatePasswordUseCase = com.nicolasrf.authentication_domain.usecase.ValidatePasswordUseCase(),
            validateEmailUseCase = com.nicolasrf.authentication_domain.usecase.ValidateEmailUseCase(
                object : com.nicolasrf.authentication_domain.matcher.EmailMatcher {
                    override fun isValid(email: String): Boolean {
                        return email.isNotEmpty()
                    }
                })
        )
        loginViewModel =
            com.nicolasrf.authentication_presentation.login.LoginViewModel(usecases, dispatcher)
    }

    @Test
    fun `inital state is empty`() {
        val state = loginViewModel.state
        assertEquals(
            com.nicolasrf.authentication_presentation.login.LoginState(
                email = "",
                password = "",
                emailError = null,
                passwordError = null,
                isLoggedIn = false,
                isLoading = false
            ),
            state
        )
    }

    @Test
    fun `given an email, verify the state updates the email`() {
        val initialState = loginViewModel.state.email
        assertEquals(initialState, "")
        loginViewModel.onEvent(LoginEvent.EmailChange("asd@asd.com"))
        val updatedState = loginViewModel.state.email
        assertEquals(
            "asd@asd.com",
            updatedState
        )
    }

    @Test
    fun `given invalid email, show email error`() {
        loginViewModel.onEvent(LoginEvent.EmailChange(""))
        loginViewModel.onEvent(LoginEvent.Login)
        val state = loginViewModel.state
        assertNotNull(state.emailError)
    }

    @Test
    fun `set valid email, Login, no email error`() {
        loginViewModel.onEvent(LoginEvent.EmailChange("whatever"))
        loginViewModel.onEvent(LoginEvent.Login)
        val state = loginViewModel.state
        assert(state.emailError == null)
    }

    @Test
    fun `set invalid password, Login, show password error`() {
        loginViewModel.onEvent(LoginEvent.PasswordChange("asd"))
        loginViewModel.onEvent(LoginEvent.Login)
        val state = loginViewModel.state
        assertNotNull(state.passwordError)
    }

    @Test
    fun `set valid password, Login, no password error`() {
        loginViewModel.onEvent(LoginEvent.PasswordChange("asdASD123"))
        loginViewModel.onEvent(LoginEvent.Login)
        val state = loginViewModel.state
        assertNull(state.passwordError)
    }

    @Test
    fun `set valid details, Login, starts loading and then logs in`() = scope.runTest {
        loginViewModel.onEvent(LoginEvent.EmailChange("whatever"))
        loginViewModel.onEvent(LoginEvent.PasswordChange("asdASD123"))
        loginViewModel.onEvent(LoginEvent.Login)
        var state = loginViewModel.state
        assertNull(state.passwordError)
        assertNull(state.emailError)
        assert(state.isLoading)
        advanceUntilIdle()
        state = loginViewModel.state
        assert(state.isLoggedIn)
    }

    @Test
    fun `set valid details but server error, Login, starts loading and then show error`() =
        scope.runTest {
            authenticationRepository.fakeError = true
            loginViewModel.onEvent(LoginEvent.EmailChange("whatever"))
            loginViewModel.onEvent(LoginEvent.PasswordChange("asdASD123"))
            loginViewModel.onEvent(LoginEvent.Login)
            var state = loginViewModel.state
            assertNull(state.passwordError)
            assertNull(state.emailError)
            assert(state.isLoading)
            advanceUntilIdle()
            state = loginViewModel.state
            assert(!state.isLoggedIn)
            assertNotNull(state.emailError)
            assert(!state.isLoading)
        }
}