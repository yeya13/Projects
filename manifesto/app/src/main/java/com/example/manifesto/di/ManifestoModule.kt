package com.example.manifesto.di

import android.content.Context
import androidx.room.Room
import com.example.manifesto.database.config.FormDB
import com.example.manifesto.database.dao.FormDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManifestoModule {
    @Singleton
    @Provides
    fun provideDispatchers(): Dispatchers = Dispatchers

    @Singleton
    @Provides
    fun provideCoroutineIODispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    @Singleton
    @Provides
    fun database(
        @ApplicationContext app: Context
    ): FormDB = Room.databaseBuilder(
        app,
        FormDB::class.java,
        "personal2")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(DB3: FormDB): FormDAO = DB3.formDao()
}
