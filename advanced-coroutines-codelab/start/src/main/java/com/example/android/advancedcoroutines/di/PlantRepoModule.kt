package com.example.android.advancedcoroutines.di

import com.example.android.advancedcoroutines.data.repository.PlantRepository
import com.example.android.advancedcoroutines.data.repository.impl.PlantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PlantRepoModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repositoryImpl: PlantRepositoryImpl) : PlantRepository
}