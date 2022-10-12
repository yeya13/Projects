package com.example.hearthstone.ui.HomePage

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.data.model.HearthstoneModel
import com.example.hearthstone.data.network.repo.HSRepo
import kotlinx.coroutines.launch


class HomeViewModel(app: Application, private val repo: HSRepo, view: View): AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<String>?>()
    val cards: LiveData<List<String>?> = _cards

    init{
        getCards()
    }

    fun getCards(){
        viewModelScope.launch {
            val cardsFetched = repo.getCards()
            Log.d("Angie", "$cardsFetched")
            _cards.postValue(cardsFetched?.classes)
        }
    }
}