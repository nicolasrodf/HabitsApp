package com.nicolasrodf.habitsapp.authentication.di

import com.nicolasrodf.habitsapp.authentication.data.matcher.EmailMatcherImpl
import com.nicolasrodf.habitsapp.authentication.data.repository.AuthenticationRepositoryImpl
import com.nicolasrodf.habitsapp.authentication.domain.matcher.EmailMatcher
import com.nicolasrodf.habitsapp.authentication.domain.repository.AuthenticationRepository
import com.nicolasrodf.habitsapp.authentication.domain.usecase.GetUserIdUseCase
import com.nicolasrodf.habitsapp.authentication.domain.usecase.LoginUseCases
import com.nicolasrodf.habitsapp.authentication.domain.usecase.LoginWithEmailUseCase
import com.nicolasrodf.habitsapp.authentication.domain.usecase.SignupUseCases
import com.nicolasrodf.habitsapp.authentication.domain.usecase.SignupWithEmailUseCase
import com.nicolasrodf.habitsapp.authentication.domain.usecase.ValidateEmailUseCase
import com.nicolasrodf.habitsapp.authentication.domain.usecase.ValidatePasswordUseCase
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
            loginWithEmailUseCase = LoginWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
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
            signupWithEmailUseCase = SignupWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideGetUserIdUseCase(repository: AuthenticationRepository): GetUserIdUseCase {
        return GetUserIdUseCase(repository)
    }
}