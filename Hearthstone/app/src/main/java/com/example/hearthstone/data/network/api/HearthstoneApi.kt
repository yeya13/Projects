package com.example.hearthstone.data.network.api

import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HearthstoneApi {

    @GET("info")
    suspend fun getCards(
        @Header("X-RapidAPI-Key") apiKey: String = "d076ef0d6emsh14986fa05e38ffdp1ea135jsn1e4319697846",
        @Header("X-RapidAPI-Host") apiHost: String = "omgvamp-hearthstone-v1.p.rapidapi.com"
    ): Response<HearthstoneModel>

    @GET("cards/classes/{name}")
    suspend fun getCardsByClass(
        @Header("X-RapidAPI-Key") apiKey: String = "d076ef0d6emsh14986fa05e38ffdp1ea135jsn1e4319697846",
        @Header("X-RapidAPI-Host") apiHost: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Path("name") className: String
    ): Response<List<HSCardsByClassModel>>

    @GET("cards/search/{name}")
    suspend fun getCardsByName(
        @Header("X-RapidAPI-Key") apiKey: String = "d076ef0d6emsh14986fa05e38ffdp1ea135jsn1e4319697846",
        @Header("X-RapidAPI-Host") apiHost: String = "omgvamp-hearthstone-v1.p.rapidapi.com",
        @Path("name") cardName: String
    ): Response<List<HSCardsByClassModel>>
}
