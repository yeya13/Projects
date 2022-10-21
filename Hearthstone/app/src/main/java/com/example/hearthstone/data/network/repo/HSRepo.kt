package com.example.hearthstone.data.network.repo

import com.example.hearthstone.data.model.HearthstoneModel
import retrofit2.Response

interface HSRepo {
    suspend fun getCards(): HearthstoneModel?
    companion object{
        fun provideHSRepoApi() = HSRepoImpl()
    }
}
