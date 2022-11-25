package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces

interface GooglePlacesRepo {
    suspend fun getPlaces(location: String): GooglePlaces?
}
