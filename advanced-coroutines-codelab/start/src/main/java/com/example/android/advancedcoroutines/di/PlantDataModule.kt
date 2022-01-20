package com.example.android.advancedcoroutines.di

import android.content.Context
import androidx.room.Room
import com.example.android.advancedcoroutines.data.service.SunflowerService
import com.example.android.advancedcoroutines.database.AppDatabase
import com.example.android.advancedcoroutines.database.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object PlantDataModule {
    @Provides
    @ViewModelScoped
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @ViewModelScoped
    fun providePlantDao(appDatabase: AppDatabase): PlantDao {
        return appDatabase.plantDao()
    }

    @Provides
    @ViewModelScoped
    fun provideNetworkService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @ViewModelScoped
    fun provideSunflowerService(networkService: Retrofit): SunflowerService {
        return networkService.create(SunflowerService::class.java)
    }

    private const val DATABASE_NAME = "sunflower-db"
}