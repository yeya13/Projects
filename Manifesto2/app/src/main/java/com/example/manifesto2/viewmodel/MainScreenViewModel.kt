package com.example.manifesto2.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifesto2.database.config.FormApp.Companion.db
import com.example.manifesto2.database.model.FormEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel: ViewModel(){
    val personalList = MutableLiveData<List<FormEntity>?>()

    fun iniciar(){
        viewModelScope.launch {
            personalList.value = withContext(Dispatchers.IO){
                db.formDao().insertPerson(arrayListOf<FormEntity>(
                    FormEntity(0,"Claudia",8183394025, "c@gmail.com", 8183394026, "Mom" ),
                    FormEntity(0,"Jair",8183394027, "j@gmail.com", 8183394026, "Mom" )
                ))
                db.formDao().getAll()
            }
            for (formEntity in personalList.value!!){
                Log.d("mensaje", "id: ${formEntity.id}, nombre: ${formEntity.fullName}, tel: ${formEntity.phoneNumber}")
            }
        }
    }

}