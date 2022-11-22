package com.example.hearthstone.ui.shopPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hearthstone.data.model.MyItem
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    app: Application
) :
    AndroidViewModel(app) {

    fun getAllItem(): ArrayList<MyItem> {
        val movieTheaters: ArrayList<MyItem> = ArrayList()
        val placeType = "Cinepolis Movie Theater"
        val type = "Movie Theater"
        val km = ""
        movieTheaters.add(MyItem("Cinepolis Patio Lincoln",placeType, type,km, LatLng(25.761685, -100.408863), false))
        movieTheaters.add(MyItem("Cinepolis Plaza Cumbres",placeType, type,km, LatLng(25.733346, -100.396985), false))
        movieTheaters.add(MyItem("Cinepolis Plaza La Quinta",placeType, type,km, LatLng(25.775264, -100.383001), false))
        movieTheaters.add(MyItem("Cinepolis Plaza Adana",placeType, type,km, LatLng(25.727014, -100.368883), false))
        return movieTheaters
    }
}
