package com.example.hearthstone.ui.searchpage

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
import com.example.hearthstone.dialogue.ErrorDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(
    app: Application,
    private val repo: HSRepo,
    private val db: HearthstoneDAO
) :
    AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<HSCardsByClassModel>?>()
    val cards: LiveData<List<HSCardsByClassModel>?> = _cards

    val _cardsID = MutableLiveData<List<String>?>()
    val cardsID: LiveData<List<String>?> = _cardsID

    private val _cardsName = MutableLiveData<List<HSCardsByClassModel>?>()
    val cardsName: LiveData<List<HSCardsByClassModel>?> = _cardsName

    val cardList = MutableLiveData<List<HearthstoneEntity>?>()

    private val _errorDialog = MutableLiveData<ErrorDialog>()
    val errorDialog: LiveData<ErrorDialog> = _errorDialog

    var _userSearch = MutableLiveData<String>()
    var userSearch: LiveData<String> = _userSearch

    var stringResource = app.getString(R.string.search_results_for)

    var _query = MutableLiveData<String>()
    var query: LiveData<String> = _query




    fun getCardsByClass(className: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val cardsFetched = repo.getCardsByClass(className)){
                is Result.Success -> {
                    _cards.postValue(cardsFetched.data)
                }
                is Result.Error -> {
                    _errorDialog.postValue(ErrorDialog())
                }

            }
        }
    }

    fun getCardsByName(cardName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when(val cardsFetched = repo.getCardsByName(cardName)){
                is Result.Success -> {
                    _cardsName.postValue(cardsFetched.data)
                }
                is Result.Error -> {
                    _errorDialog.postValue(ErrorDialog())
                }


            }
        }
        _userSearch.value = "${stringResource} '$cardName'"
    }

    fun getAllID() {
        viewModelScope.launch(Dispatchers.IO) {
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
            withContext(Dispatchers.IO){
                db.insertCard(obEntity)
            }
        }
    }

    fun deleteCard(hs: HSCardsByClassModel) {
        val obEntity = getCardData(hs)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.removeCard(obEntity)
            }
        }
    }

    fun getAllCards(){
        viewModelScope.launch {
            cardList.value = withContext(Dispatchers.IO){
                db.getAll()
            }
        }
    }

    fun checkFavorite(value: Boolean, card: HSCardsByClassModel) {
        if (value) {
            insertCard(card)
        } else {
            deleteCard(card)
        }
    }

    fun onTextChanged(text: CharSequence) {
        _query.value = text.toString()
    }

    fun validateSearch(): Boolean {
        var isValid = true
        val textSearch = _query.value
        if (textSearch.isNullOrEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun buttonSearch() {
        if (validateSearch()) {
            val nameCard =_query.value.toString()
            getCardsByName(nameCard)
        }
    }
}
