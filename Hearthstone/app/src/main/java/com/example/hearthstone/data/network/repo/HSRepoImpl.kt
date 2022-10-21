package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.network.api.HearthstoneApi
import com.example.hearthstone.data.network.retrofit.SingletonRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

    override suspend fun getCardsByClass(className: String): List<HSCardsByClassModel>? {
        return withContext(Dispatchers.IO){
            val apiHSApi = singletonRetrofit
            val response = apiHSApi.getCardsByClass(className = className)
            response.body()
        }
    }

    override suspend fun getCardsByName(cardName: String): List<HSCardsByClassModel>? {
        return withContext(Dispatchers.IO){
            val apiHSApi = singletonRetrofit
            val response = apiHSApi.getCardsByName(cardName = cardName)
            response.body()
        }
    }
}
