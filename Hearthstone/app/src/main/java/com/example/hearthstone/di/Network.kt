package com.example.hearthstone.di

import com.example.hearthstone.data.network.api.GooglePlacesApi
import com.example.hearthstone.data.network.api.HearthstoneApi
import com.example.hearthstone.data.network.repo.GooglePlacesRepo
import com.example.hearthstone.data.network.repo.GooglePlacesRepoImpl
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.data.network.repo.HSRepoImpl
import com.example.hearthstone.data.network.retrofit.SingletonRetrofit
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
    fun provideHSApi(): HearthstoneApi {
        return SingletonRetrofit
            .retrofitProvider(
                serviceClass = HearthstoneApi::class.java,
                baseUrl = "https://omgvamp-hearthstone-v1.p.rapidapi.com/")
    }

    @Singleton
    @Provides
    fun provideHSRepoApi(api: HearthstoneApi, dispatcher: Dispatchers): HSRepo{
        return HSRepoImpl(api, dispatcher)
    }

    @Singleton
    @Provides
    fun provideGooglePlacesApi(): GooglePlacesApi {
        return SingletonRetrofit
            .retrofitProvider(
                serviceClass = GooglePlacesApi::class.java,
                baseUrl = "https://maps.googleapis.com/")
    }

    @Singleton
    @Provides
    fun provideGooglePlacesRepoApi(api: GooglePlacesApi, dispatcher: Dispatchers): GooglePlacesRepo{
        return GooglePlacesRepoImpl(api, dispatcher)
    }
}
