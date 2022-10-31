package com.example.hearthstone.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.database.config.HearthstoneDB
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.ui.cardOverview.CardOverviewViewModel
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
object AppComponent {
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
    ): HearthstoneDB = Room.databaseBuilder(
    app,
    HearthstoneDB::class.java,
    "hearthstone")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDao(db: HearthstoneDB): HearthstoneDAO = db.hearthstoneDao()

}
