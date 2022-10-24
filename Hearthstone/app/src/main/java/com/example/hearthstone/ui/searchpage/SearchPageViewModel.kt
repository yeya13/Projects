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
import kotlinx.coroutines.launch

class SearchPageViewModel(app: Application, private val repo: HSRepo) :
    AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<HSCardsByClassModel>?>()
    val cards: LiveData<List<HSCardsByClassModel>?> = _cards

    private val _cardsName = MutableLiveData<List<HSCardsByClassModel>?>()
    val cardsName: LiveData<List<HSCardsByClassModel>?> = _cardsName



    fun getCardsByClass(className: String) {
        viewModelScope.launch {
            val cardsFetched = repo.getCardsByClass(className)
            _cards.postValue(cardsFetched)
        }
    }

    fun getCardsByName(cardName: String){
        viewModelScope.launch {
            val cardsFetched = repo.getCardsByName(cardName)
            _cardsName.postValue(cardsFetched)
        }
    }
}
