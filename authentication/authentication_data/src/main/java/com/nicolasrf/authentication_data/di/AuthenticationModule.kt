package com.nicolasrf.authentication_data.di

import com.nicolasrf.authentication_data.matcher.EmailMatcherImpl
import com.nicolasrf.authentication_data.repository.AuthenticationRepositoryImpl
import com.nicolasrf.authentication_domain.matcher.EmailMatcher
import com.nicolasrf.authentication_domain.repository.AuthenticationRepository
import com.nicolasrf.authentication_domain.usecase.GetUserIdUseCase
import com.nicolasrf.authentication_domain.usecase.LoginUseCases
import com.nicolasrf.authentication_domain.usecase.LoginWithEmailUseCase
import com.nicolasrf.authentication_domain.usecase.LogoutUseCase
import com.nicolasrf.authentication_domain.usecase.SignupUseCases
import com.nicolasrf.authentication_domain.usecase.SignupWithEmailUseCase
import com.nicolasrf.authentication_domain.usecase.ValidateEmailUseCase
import com.nicolasrf.authentication_domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository {
        return AuthenticationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(
        repository: AuthenticationRepository,
        emailMatcher: EmailMatcher
    ): LoginUseCases {
        return LoginUseCases(
            loginWithEmailUseCase = LoginWithEmailUseCase(
                repository
            ),
            validateEmailUseCase = ValidateEmailUseCase(
                emailMatcher
            ),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideSignupUseCases(
        repository: AuthenticationRepository,
        emailMatcher: EmailMatcher
    ): SignupUseCases {
        return SignupUseCases(
            signupWithEmailUseCase = SignupWithEmailUseCase(
                repository
            ),
            validateEmailUseCase = ValidateEmailUseCase(
                emailMatcher
            ),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideGetUserIdUseCase(repository: AuthenticationRepository): GetUserIdUseCase {
        return GetUserIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthenticationRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }
}