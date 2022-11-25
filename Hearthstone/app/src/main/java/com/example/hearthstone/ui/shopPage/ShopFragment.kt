package com.example.hearthstone.ui.shopPage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.hearthstone.R
import com.example.hearthstone.data.model.ClusterRender
import com.example.hearthstone.data.model.MyItem
import com.example.hearthstone.databinding.FragmentMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.SphericalUtil
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : Fragment(), OnMapReadyCallback{
    private val viewModel: ShopViewModel by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentMapBinding
    private lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var clusterManager: ClusterManager<MyItem>
    private var lastSelectedMovieMarker: MyItem? = null
    lateinit var currentLocation: LatLng
    lateinit var destinationLocation: LatLng
    var distance: Double = 0.0

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_map,
            container, false
        )

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_maps_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
        setUpClusterer()
        itemSelect()
    }

    private fun isPermissionsGranted() = ActivityCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED


    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
            fusedLocation.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(currentLocation, 12f),
                        3000,
                        null
                    )
                    val lat = currentLocation.latitude.toString()
                    val long = currentLocation.longitude.toString()
                    val location = "$lat,$long"
                    viewModel.getPlaces(location)
                    map.addMarker(MarkerOptions().position(currentLocation).title(getString(R.string.my_location)))
                    map.uiSettings.isMyLocationButtonEnabled = false
                    map.uiSettings.isZoomControlsEnabled = true
                }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity as FragmentActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                context,
                getString(R.string.go_settings_accept_permissions),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                activity as FragmentActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    private fun setUpClusterer() {
        clusterManager = ClusterManager(context, map)
        // Point the map's listeners at the listeners implemented by the cluster manager
        map.setOnCameraIdleListener(clusterManager)

        viewModel.places.observe(viewLifecycleOwner){ list ->
            list?.forEach {result ->
                clusterManager.addItems(viewModel.getAllItem(result.name, result.geometry))
            }
        }
        clusterManager.cluster()
        val render = ClusterRender(requireContext(), map = map, clusterManager = clusterManager)
        clusterManager.renderer = render

    }


    private fun itemSelect() {
        clusterManager.setOnClusterItemClickListener { myItem ->
            val clusterRender = clusterManager.renderer as ClusterRender
            if (lastSelectedMovieMarker != null) {
                lastSelectedMovieMarker?.let {
                    it.isSelected = false
                    val marker = clusterRender.getMarker(it)
                    clusterRender.onClusterItemChange(myItem,marker)
                }
            }

            myItem.isSelected = true
            val marker = clusterRender.getMarker(myItem)
            clusterRender.onClusterItemChange(myItem, marker)
            destinationLocation = myItem.latLng
            distance = SphericalUtil.computeDistanceBetween(currentLocation, destinationLocation)
            val km = String.format("%.2f", distance / 1000)
            myItem.km = "$km km"
            binding.myItem = myItem
            binding.movieTheaterDirection.visibility = View.VISIBLE
            binding.movieTheaterName.visibility = View.VISIBLE
            lastSelectedMovieMarker = myItem
            true
        }
    }
}
