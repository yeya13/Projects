package com.example.hearthstone.ui.homepage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hearthstone.R
import com.example.hearthstone.data.model.Result
import com.example.hearthstone.data.network.repo.HSRepo
import com.example.hearthstone.dialogue.ErrorDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    app: Application,
    private val repo: HSRepo,
    private val dispatcher: Dispatchers
) : AndroidViewModel(app) {
    private val _cards = MutableLiveData<List<String>?>()
    val cards: LiveData<List<String>?> = _cards

    private val _errorDialog = MutableLiveData<ErrorDialog>()
    val errorDialog: LiveData<ErrorDialog> = _errorDialog

    var _userSearch = MutableLiveData<String>()
    var userSearch: LiveData<String> = _userSearch

    var _query = MutableLiveData<String>()

    init {
        getCardsByClass()
    }

    private fun getCardsByClass() {
        viewModelScope.launch(dispatcher.IO) {
            when (val cardsFetched = repo.getCards()) {
                is Result.Success -> {
                    _cards.postValue(cardsFetched.data?.classes)
                }
                is Result.Error -> {
                    _errorDialog.postValue(ErrorDialog())
                }
            }
        }
    }

    fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
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
            _userSearch.value = _query.value.toString()
        }
    }
}
