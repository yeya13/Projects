package com.example.hearthstone.ui.shopPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.MyItem
import com.example.hearthstone.data.model.modelGooglePlaces.Geometry
import com.example.hearthstone.data.model.modelGooglePlaces.ResultProperties
import com.example.hearthstone.data.network.repo.GooglePlacesRepo
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    app: Application,
    private val repo: GooglePlacesRepo,
    private val dispatcher: Dispatchers
) : AndroidViewModel(app) {

    private val _places = MutableLiveData<List<ResultProperties>?>()
    val places: LiveData<List<ResultProperties>?> = _places


    fun getPlaces(location: String) {
        viewModelScope.launch(dispatcher.IO) {
            val placesFetched = repo.getPlaces(location)
            _places.postValue(placesFetched?.results)
        }
    }

    fun getAllItem(name: String, geometry: Geometry): ArrayList<MyItem> {
        var isSelected = false
        val movieTheaters: ArrayList<MyItem> = ArrayList()
        val placeType = "Movie Theater"
        val km = ""
        val location: LatLng
        location = LatLng(geometry.location.lat, geometry.location.lng)
        movieTheaters.add(MyItem(name,placeType, placeType,km, location, isSelected))
        return movieTheaters
    }
}
