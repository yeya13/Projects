package com.example.hearthstone.ui.searchpage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(
    app: Application,
    private val repo: HSRepo,
    private val dispatcher: Dispatchers,
    private val db: HearthstoneDAO
) :
    AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<HSCardsByClassModel>?>()
    val cards: LiveData<List<HSCardsByClassModel>?> = _cards

    val _cardsID = MutableLiveData<List<String>?>()
    val cardsID: LiveData<List<String>?> = _cardsID

    private val _cardsName = MutableLiveData<List<HSCardsByClassModel>?>()
    val cardsName: LiveData<List<HSCardsByClassModel>?> = _cardsName

    private val cardList = MutableLiveData<List<HearthstoneEntity>?>()

    var _userSearch = MutableLiveData<String>()
    var userSearch: LiveData<String> = _userSearch

    var stringResource = app.getString(R.string.search_results_for)



    fun getCardsByClass(className: String) {
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getCardsByClass(className)
            _cards.postValue(cardsFetched)
        }
    }

    fun getCardsByName(cardName: String) {
        viewModelScope.launch(dispatcher.IO) {
            val cardsFetched = repo.getCardsByName(cardName)
            _cardsName.postValue(cardsFetched)
        }
        _userSearch.value = "${stringResource} '$cardName'"
    }

    fun getAllID() {
        viewModelScope.launch(dispatcher.IO) {
            _cardsID.postValue(db.getAllId())
        }
    }

    fun getCardData(hs: HSCardsByClassModel): HearthstoneEntity {
        return HearthstoneEntity(
            id = hs.cardId ?: "",
            name = hs.name,
            type = hs.type,
            rarity = hs.rarity,
            cardSet = hs.cardSet,
            img = hs.img,
            text = hs.text,
            playerClass = hs.playerClass
        )
    }

    fun insertCard(hs: HSCardsByClassModel){
        val obEntity = getCardData(hs)
        viewModelScope.launch {
            withContext(dispatcher.IO){
                db.insertCard(obEntity)
            }
        }
    }

    fun deleteCard(hs: HSCardsByClassModel) {
        val obEntity = getCardData(hs)
        viewModelScope.launch {
            withContext(dispatcher.IO) {
                db.removeCard(obEntity)
            }
        }
    }

    fun getAllCards(){
        viewModelScope.launch {
            cardList.value = withContext(dispatcher.IO){
                db.getAll()
            }
        }
    }
}
