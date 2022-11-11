package com.example.hearthstone.ui.shopPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    app: Application,
    private val repo: HSRepo,
    private val dispatcher: Dispatchers,
    private val db: HearthstoneDAO
) :
    AndroidViewModel(app) {

    fun getAllItem(): ArrayList<MyItem> {
        val movieTheaters: ArrayList<MyItem> = ArrayList()
        movieTheaters.add(MyItem("Cinepolis Patio Lincoln", LatLng(25.761685, -100.408863)))
        movieTheaters.add(MyItem("Cinepolis Plaza Cumbres", LatLng(25.733346, -100.396985)))
        movieTheaters.add(MyItem("Cinepolis Plaza La Quinta", LatLng(25.775264, -100.383001)))
        movieTheaters.add(MyItem("Cinepolis Plaza Adana", LatLng(25.727014, -100.368883)))
        return movieTheaters
    }
}
