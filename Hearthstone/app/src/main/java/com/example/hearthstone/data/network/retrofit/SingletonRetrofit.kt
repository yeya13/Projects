package com.example.hearthstone.data.network.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SingletonRetrofit{
    fun <T> retrofitProvider(
        client: OkHttpClient = OkHttpClient().newBuilder().build(),
        serviceClass: Class<T>,
        baseUrl: String
    ): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(serviceClass)
    }
}
