package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces
import com.example.hearthstone.data.model.Result

interface GooglePlacesRepo {
    suspend fun getPlaces(location: String): Result<GooglePlaces?>
}
