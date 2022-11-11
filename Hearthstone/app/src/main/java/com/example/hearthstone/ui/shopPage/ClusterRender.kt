package com.example.hearthstone.ui.shopPage

import android.content.Context
import android.widget.Toast
import com.example.hearthstone.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterRender(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<MyItem>
) : DefaultClusterRenderer<MyItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.unnamed_copy_5))
    }

    fun onClusterItemChange(item: MyItem, marker: Marker) {
        if (item.isSelected) {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.logo_copy))
        } else {
            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.unnamed_copy_5))
        }
    }
}
