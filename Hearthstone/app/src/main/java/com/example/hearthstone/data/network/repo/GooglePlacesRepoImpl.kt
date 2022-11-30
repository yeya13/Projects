package com.example.hearthstone.data.network.repo

import android.util.Log
import android.view.View
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces
import com.example.hearthstone.data.network.api.GooglePlacesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GooglePlacesRepoImpl @Inject constructor(
    private val api: GooglePlacesApi,
    private val dispatcher: Dispatchers,
) : GooglePlacesRepo {

    override suspend fun getPlaces(location: String): Result<GooglePlaces?> {
        return withContext(dispatcher.IO) {
            val apiGPApi = api
            val response = apiGPApi.getPlaces(location = location)
            response.body()
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else {
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }
}
