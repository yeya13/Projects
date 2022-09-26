package com.example.manifesto.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifesto.database.config.FormApp.Companion.DB3
import com.example.manifesto.database.models.FormEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel: ViewModel(){
    val personalList = MutableLiveData<List<FormEntity>?>()

    fun testDB(){
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                DB3.formDao().getAll()
            }
            for (formEntity in personalList.value!!){
                Log.d("mensaje", "id: ${formEntity.id}, nombre: ${formEntity.fullName}, tel: ${formEntity.phoneNumber}")
            }
        }
    }


}