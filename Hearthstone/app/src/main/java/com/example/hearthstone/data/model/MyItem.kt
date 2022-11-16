package com.example.hearthstone.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


data class MyItem(var movieTheaterName: String, var placeType: String, var type: String,var km: String, var latLng: LatLng) : ClusterItem {

    var isSelected: Boolean = false

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
