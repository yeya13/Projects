package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.network.api.HearthstoneApi
import com.example.hearthstone.data.network.retrofit.SingletonRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HSRepoImpl @Inject constructor(private val api: HearthstoneApi, private val dispatcher: Dispatchers) : HSRepo {

    override suspend fun getCards(): HearthstoneModel? {
        return withContext(dispatcher.IO) {
            val apiHSApi = api
            val response = apiHSApi.getCards()
            response.body()
        }
    }

    override suspend fun getCardsByClass(className: String): List<HSCardsByClassModel>? {
        return withContext(Dispatchers.IO){
            val apiHSApi = api
            val response = apiHSApi.getCardsByClass(className = className)
            response.body()
        }
    }

    override suspend fun getCardsByName(cardName: String): List<HSCardsByClassModel>? {
        return withContext(Dispatchers.IO){
            val apiHSApi = api
            val response = apiHSApi.getCardsByName(cardName = cardName)
            response.body()
        }
    }
}
