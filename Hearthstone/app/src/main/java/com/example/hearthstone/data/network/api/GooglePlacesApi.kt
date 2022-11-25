package com.example.hearthstone.data.network.api

import com.example.hearthstone.data.model.modelGooglePlaces.GooglePlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {

    @GET("maps/api/place/nearbysearch/json")
    suspend fun getPlaces(
        @Query("location") location: String,
        @Query("type") type: String = "movie_theater",
        @Query("radius") radius: String =  "1500",
        @Query("key") key: String = ""
    ): Response<GooglePlaces>
}
