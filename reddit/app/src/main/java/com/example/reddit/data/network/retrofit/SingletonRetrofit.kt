package com.example.reddit.data.network.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SingletonRetrofit {
    val interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    fun <T> retrofitProvider(
        client: OkHttpClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build(),
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
