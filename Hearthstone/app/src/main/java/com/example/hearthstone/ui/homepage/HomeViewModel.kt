package com.example.hearthstone.ui.homepage

import android.app.Application
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
}
