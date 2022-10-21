package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.HearthstoneModel

interface HSRepo {
    suspend fun getCards(): HearthstoneModel?

    suspend fun getCardsByClass(className: String): List<HSCardsByClassModel>?

    suspend fun getCardsByName(cardName: String): List<HSCardsByClassModel>?

}
