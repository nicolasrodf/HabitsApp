package com.nicolasrf.authentication_domain.usecase

data class LoginUseCases(
    val loginWithEmailUseCase: LoginWithEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)