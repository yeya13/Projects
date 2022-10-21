package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.network.api.HearthstoneApi
import com.example.hearthstone.data.network.retrofit.SingletonRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class HSRepoImpl : HSRepo {
    private val singletonRetrofit = SingletonRetrofit.retrofitProvider(
        serviceClass = HearthstoneApi::class.java,
        baseUrl = "https://omgvamp-hearthstone-v1.p.rapidapi.com/")

    override suspend fun getCards(): HearthstoneModel? {
        return withContext(Dispatchers.IO) {
            val apiHSApi = singletonRetrofit
            val response = apiHSApi.getCards()
            response.body()
        }
    }
}
