package com.example.hearthstone.ui.favoritepage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    app: Application,
    private val db: HearthstoneDAO
) : AndroidViewModel(app) {
    private val _cardList = MutableLiveData<List<HearthstoneEntity>?>()
    val cardList: LiveData<List<HearthstoneEntity>?> = _cardList

    fun getAllCards(){
        viewModelScope.launch {
            _cardList.value = withContext(Dispatchers.IO){
                db.getAll()
            }
        }
    }

    fun deleteUser(hs: HearthstoneEntity) {
        val obFormEntity = getCardData(hs)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.removeCard(obFormEntity)
            }
            getAllCards()
        }
    }

    fun getCardData(hs: HearthstoneEntity): HearthstoneEntity {
        return HearthstoneEntity(
            id = hs.id,
            name = hs.name,
            type = hs.type,
            rarity = hs.rarity,
            cardSet = hs.cardSet,
            img = hs.img,
            text = hs.text,
            playerClass = hs.playerClass
        )
    }
}
