package com.example.hearthstone.data.network.repo

import android.view.View
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.network.api.HearthstoneApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class HSRepoImpl @Inject constructor(
    private val api: HearthstoneApi,
    private val dispatcher: Dispatchers
) : HSRepo {

    override suspend fun getCards(): Result<HearthstoneModel?> {
        return withContext(dispatcher.IO) {
            val apiHSApi = api
            val response = apiHSApi.getCards()
            response.body()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }

    override suspend fun getCardsByClass(className: String): Result<List<HSCardsByClassModel>?> {
        return withContext(Dispatchers.IO) {
            val apiHSApi = api
            val response = apiHSApi.getCardsByClass(className = className)
            response.body()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }

    override suspend fun getCardsByName(cardName: String): Result<List<HSCardsByClassModel>?> {
        return withContext(Dispatchers.IO) {
            val apiHSApi = api
            val response = apiHSApi.getCardsByName(cardName = cardName)
            response.body()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }
}
