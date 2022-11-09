package com.example.hearthstone.ui.cardOverview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
class CardOverviewViewModel @Inject constructor(
    app: Application,
    private val dispatcher: Dispatchers,
    private val db: HearthstoneDAO
) : AndroidViewModel(app) {
    private val _cards = MutableLiveData<HSCardsByClassModel>()
    var cards: LiveData<HSCardsByClassModel> = _cards

    private val cardList = MutableLiveData<List<HearthstoneEntity>?>()

    private val _fav = MutableLiveData<Boolean>(false)
    var fav: LiveData<Boolean> = _fav


    fun getCardData(): HearthstoneEntity {
        return HearthstoneEntity(
            id = _cards.value?.cardId ?: "",
            name = _cards.value?.name,
            type = _cards.value?.type,
            rarity = _cards.value?.rarity,
            cardSet = _cards.value?.cardSet,
            img = _cards.value?.img,
            text = _cards.value?.text,
            playerClass = _cards.value?.playerClass
        )
    }

    fun insertCard(){
        val obFormEntity = getCardData()
        viewModelScope.launch {
            withContext(dispatcher.IO){
                db.insertCard(obFormEntity)
            }
            getAllCards()
        }
    }

    fun getAllCards(){
        viewModelScope.launch {
            cardList.value = withContext(dispatcher.IO){
                db.getAll()
            }
            cardList.value?.let {
                for (HSEntity in it){
                    Log.d("mensaje", "id: ${HSEntity.id}, nombre: ${HSEntity.name}, tel: ${HSEntity.type}")
                }
            }
        }
    }

    fun queryCard(hsCard: HSCardsByClassModel) {
        _cards.value = hsCard
        viewModelScope.launch {
            withContext(dispatcher.IO) {
                val result = db.doDataQuery(hsCard.cardId)
                if (result == null) {
                    _fav.postValue(false)
                } else {
                    _fav.postValue(true)
                }
            }
        }
    }

    fun deleteUser() {
        val obFormEntity = getCardData()
        viewModelScope.launch {
            withContext(dispatcher.IO) {
                db.removeCard(obFormEntity)
            }
            getAllCards()
        }
    }
}
