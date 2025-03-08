package com.example.hanyarunrun.di

import android.content.Context
import androidx.room.Room
import com.example.hanyarunrun.data.AppDatabase
import com.example.hanyarunrun.data.AppDao
import com.example.hanyarunrun.data.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAppDao(database: AppDatabase): AppDao {
        return database.appDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(appDao: AppDao): RoomRepository {
        return RoomRepository(appDao)
    }
}