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
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel (app: Application, private val repo: HSRepo): AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<String>?>()
    val cards: LiveData<List<String>?> = _cards

    init{
        getCardsByClass()
    }

    private fun getCardsByClass(){
        viewModelScope.launch {
            val cardsFetched = repo.getCards()
            Log.d("Angie", "$cardsFetched")
            _cards.postValue(cardsFetched?.classes)
        }
    }
}
