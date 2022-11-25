package com.example.hearthstone.ui.cardOverview

import android.app.Application
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.R
import com.example.hearthstone.data.model.HSCardsByClassModel
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

    private var _card = MutableLiveData<HSCardsByClassModel>()
    var card: LiveData<HSCardsByClassModel> = _card

    fun getInformationCards(cardModel: HSCardsByClassModel){
        _card.value = cardModel.copy(
            text = cardModel.text?.let { text ->
                HtmlCompat.fromHtml(
                    "${getApplication<Application>().resources.getString(R.string.effect_text)} $text",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            },
            type = cardModel.type?.let { type ->
                HtmlCompat.fromHtml(
                    "${getApplication<Application>().resources.getString(R.string.type_text)} $type",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            },
            rarity = cardModel.rarity?.let { rarity ->
                HtmlCompat.fromHtml(
                    "${getApplication<Application>().resources.getString(R.string.rarity_text)} $rarity",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            },
            cardSet = cardModel.cardSet?.let { set ->
                HtmlCompat.fromHtml(
                    "${getApplication<Application>().resources.getString(R.string.set_text)} $set",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
            }
        )
    }

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
