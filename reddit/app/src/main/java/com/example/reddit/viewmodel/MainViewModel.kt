package com.example.reddit.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.reddit.data.model.ArticleModel
import com.example.reddit.data.model.ChildProperty
import com.example.reddit.data.model.Children
import com.example.reddit.data.model.Result
import com.example.reddit.data.network.repo.RedditRepo
import com.example.reddit.dialogues.ErrorDialog
import kotlinx.coroutines.launch

class MainViewModel(app: Application, private val repo: RedditRepo, view: View): AndroidViewModel(app) {
    private val _author = MutableLiveData<List<ChildProperty>?>()
    val author: LiveData<List<ChildProperty>?> = _author
    var context = view.context

    init{
        getAuthor()
    }

    fun getAuthor(){
        viewModelScope.launch {
            //val authorFetched = repo.getAuthor()
            //Log.d("Angie", "$authorFetched")
            //_author.postValue(authorFetched?.data?.children)

            when(val authorFetched = repo.getAuthor()){
                is Result.Success -> {
                    _author.postValue(authorFetched?.data?.data?.children)
                    Log.d("Zelda", "Success")
                }
                is Result.Error ->{
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    ErrorDialog().show(fragmentManager, ErrorDialog::class.java.name)
                    Log.d("Zelda", "Failure")
                }
            }
        }
    }
}