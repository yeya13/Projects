package com.example.hearthstone.ui.shopPage

import android.app.Application
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.MyItem
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.model.modelGooglePlaces.Geometry
import com.example.hearthstone.data.model.modelGooglePlaces.ResultProperties
import com.example.hearthstone.data.network.repo.GooglePlacesRepo
import com.example.hearthstone.dialogue.ErrorDialog
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    app: Application,
    private val repo: GooglePlacesRepo
) : AndroidViewModel(app) {

    private val _places = MutableLiveData<List<ResultProperties>?>()
    val places: LiveData<List<ResultProperties>?> = _places

    private val _errorDialog = MutableLiveData<ErrorDialog>()
    val errorDialog: LiveData<ErrorDialog> = _errorDialog


    fun getPlaces(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when( val placesFetched = repo.getPlaces(location)){
                is Result.Success -> {
                    _places.postValue(placesFetched.data?.results)
                }
                is Result.Error -> {
                    _errorDialog.postValue(ErrorDialog())
                }
            }
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
