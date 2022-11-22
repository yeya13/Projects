package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.model.Result

interface HSRepo {
    suspend fun getCards(): Result<HearthstoneModel?>

    suspend fun getCardsByClass(className: String): Result<List<HSCardsByClassModel>?>

    suspend fun getCardsByName(cardName: String): Result<List<HSCardsByClassModel>?>
}
