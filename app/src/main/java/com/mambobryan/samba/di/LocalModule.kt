package com.mambobryan.samba.di

import android.app.Application
import androidx.room.Room
import com.mambobryan.samba.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: AppDatabase.Callback
    ): AppDatabase = Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .addCallback(callback)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCharactersDao(database: AppDatabase) = database.charactersDao()

    @Provides
    fun provideCharacterKeysDao(database: AppDatabase) = database.characterKeysDao()

    @Provides
    fun provideNotesDao(database: AppDatabase) = database.notesDao()

}