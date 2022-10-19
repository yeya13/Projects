package com.example.hearthstone.ui.homepage

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.network.repo.HSRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (app: Application, private val repo: HSRepo, private val dispatcher: Dispatchers): AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<String>?>()
    val cards: LiveData<List<String>?> = _cards

    init{
        getCardsByClass()
    }

    private fun getCardsByClass(){
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getCards()
            Log.d("Angie", "$cardsFetched")
            _cards.postValue(cardsFetched?.classes)
        }
    }
}
