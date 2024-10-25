package com.nicolasrf.authentication_domain.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordResult {
        return when {
            password.length < MAX_PASSWORD_LENGTH -> PasswordResult.INVALID_LENGTH
            !password.any { it.isLowerCase() } -> PasswordResult.INVALID_LOWERCASE
            !password.any { it.isUpperCase() } -> PasswordResult.INVALID_UPPERCASE
            !password.any { it.isDigit() } -> PasswordResult.INVALID_DIGITS
            else -> PasswordResult.VALID
        }
    }
    companion object {
        const val MAX_PASSWORD_LENGTH = 8
    }
}

enum class PasswordResult {
    VALID,
    INVALID_LOWERCASE,
    INVALID_UPPERCASE,
    INVALID_DIGITS,
    INVALID_LENGTH
}