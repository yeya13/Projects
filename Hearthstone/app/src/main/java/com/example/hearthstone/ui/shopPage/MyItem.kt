package com.example.hearthstone.ui.shopPage

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


class MyItem(private var movieTheaterName: String, var latLng: LatLng) : ClusterItem {

    var isSelected: Boolean = false
    lateinit var movieTheaters: MutableList<MyItem>

    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String {
        return movieTheaterName
    }

    override fun getSnippet(): String {
        return ""
    }
}
