package com.example.hearthstone.ui.searchpage

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private val _errorDialog = MutableLiveData<ErrorDialog>()
    val errorDialog: LiveData<ErrorDialog> = _errorDialog


    fun getCardsByClass(className: String) {
        viewModelScope.launch(dispatcher.IO) {
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
        viewModelScope.launch(dispatcher.IO) {
            when(val cardsFetched = repo.getCardsByName(cardName)){
                is Result.Success -> {
                    _cardsName.postValue(cardsFetched.data)
                }
                is Result.Error -> {
                    _errorDialog.postValue(ErrorDialog())
                }

            }
        }
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
