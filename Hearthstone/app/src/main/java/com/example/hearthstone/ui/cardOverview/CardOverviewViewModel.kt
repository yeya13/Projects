package com.example.hearthstone.ui.cardOverview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.network.repo.HSRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardOverviewViewModel @Inject constructor(
    app: Application,
    private val repo: HSRepo,
    private val dispatcher: Dispatchers
) :
    AndroidViewModel(app) {
    private val _cardName = MutableLiveData<List<HSCardsByClassModel>?>()
    val cardName: LiveData<List<HSCardsByClassModel>?> = _cardName

    //This init is a test to verify that the call works
    init {
        getSingleCard("Mana Bind")
    }

    private fun getSingleCard(cardName: String){
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getSingleCard(cardName)
            _cardName.postValue(cardsFetched)
        }
    }
}
