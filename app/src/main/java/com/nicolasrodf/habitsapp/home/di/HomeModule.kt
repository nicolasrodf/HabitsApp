package com.nicolasrodf.habitsapp.home.di

import com.nicolasrodf.habitsapp.home.data.repository.HomeRepositoryImpl
import com.nicolasrodf.habitsapp.home.domain.detail.usecase.DetailUseCases
import com.nicolasrodf.habitsapp.home.domain.detail.usecase.GetHabitByIdUseCase
import com.nicolasrodf.habitsapp.home.domain.detail.usecase.InsertHabitUseCase
import com.nicolasrodf.habitsapp.home.domain.home.usecase.CompleteHabitUseCase
import com.nicolasrodf.habitsapp.home.domain.home.usecase.GetHabitsForDateUseCase
import com.nicolasrodf.habitsapp.home.domain.home.usecase.HomeUseCases
import com.nicolasrodf.habitsapp.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun provideHomeUseCases(repository: HomeRepository): HomeUseCases {
        return HomeUseCases(
            completeHabitUseCase = CompleteHabitUseCase(repository),
            getHabitsForDateUseCase = GetHabitsForDateUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideDetailUseCases(repository: HomeRepository): DetailUseCases {
        return DetailUseCases(
            getHabitByIdUseCase = GetHabitByIdUseCase(repository),
            insertHabitUseCase = InsertHabitUseCase(repository)
        )
    }

    @Singleton
    @Provides
    fun provideHomeRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }
}
