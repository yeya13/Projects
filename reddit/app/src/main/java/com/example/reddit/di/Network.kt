package com.example.reddit.di

import com.example.reddit.data.network.api.RedditApi
import com.example.reddit.data.network.repo.RedditRepo
import com.example.reddit.data.network.repo.RedditRepoImpl
import com.example.reddit.data.network.retrofit.SingletonRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Network {

    @Singleton
    @Provides
    fun provideRedditApi(): RedditApi {
        return SingletonRetrofit
            .retrofitProvider(
                serviceClass = RedditApi::class.java,
                baseUrl = "https://www.reddit.com/r/")
    }

    @Singleton
    @Provides
    fun provideRedditRepoApi(api: RedditApi, dispatcher: Dispatchers): RedditRepo{
        return RedditRepoImpl(api, dispatcher)
    }

}
