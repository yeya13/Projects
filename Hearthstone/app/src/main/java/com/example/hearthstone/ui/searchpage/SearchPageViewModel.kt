package com.example.hearthstone.ui.searchpage

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.network.repo.HSRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(app: Application, private val repo: HSRepo, private val dispatcher: Dispatchers) :
    AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<HSCardsByClassModel>?>()
    val cards: LiveData<List<HSCardsByClassModel>?> = _cards

    private val _cardsName = MutableLiveData<List<HSCardsByClassModel>?>()
    val cardsName: LiveData<List<HSCardsByClassModel>?> = _cardsName



    fun getCardsByClass(className: String) {
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getCardsByClass(className)
            Log.d("Cards", "$cardsFetched")
            _cards.postValue(cardsFetched)
        }
    }

    fun getCardsByName(cardName: String){
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getCardsByName(cardName)
            Log.d("Angie", "$cardsFetched")
            _cardsName.postValue(cardsFetched)
        }
    }
}
