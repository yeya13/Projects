package com.example.manifesto.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.manifesto.database.dao.FormDAO
import com.example.manifesto.database.models.FormEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    app: Application,
    private val dispatcher: Dispatchers,
    private val DB3: FormDAO
) : AndroidViewModel(app) {
    val personalList = MutableLiveData<List<FormEntity>?>()

    fun testDB() {
        viewModelScope.launch {
            personalList.value = withContext(dispatcher.IO) {
                DB3.getAll()
            }
        }
    }

    fun deleteUser(obFormEntity: FormEntity) {
        viewModelScope.launch {
            withContext(dispatcher.IO) {
                DB3.deletePerson(obFormEntity)
            }
            testDB()
        }
    }
}
