package com.example.reddit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reddit.data.model.ChildProperty
import com.example.reddit.data.model.Result
import com.example.reddit.data.network.repo.RedditRepo
import com.example.reddit.dialogues.ErrorDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val repo: RedditRepo
): AndroidViewModel(app) {

    private val _author = MutableLiveData<List<ChildProperty>?>()
    val author: LiveData<List<ChildProperty>?> = _author

    private val _errorDialog = MutableLiveData<ErrorDialog>()
    val errorDialog: LiveData<ErrorDialog> = _errorDialog



    init{
        getAuthor()
    }

    fun getAuthor(){
        viewModelScope.launch {
            when(val authorFetched = repo.getAuthor()){
                is Result.Success -> {
                    _author.postValue(authorFetched.data?.data?.children)
                }
                is Result.Error ->{
                    _errorDialog.postValue(ErrorDialog())
                }
            }
        }
    }
}
