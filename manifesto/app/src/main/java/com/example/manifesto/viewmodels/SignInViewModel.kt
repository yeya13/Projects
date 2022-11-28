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
class SignInViewModel @Inject constructor(
    app: Application,
    private val dispatcher: Dispatchers,
    private val DB3: FormDAO
): AndroidViewModel(app) {

    val id= MutableLiveData<Long>(0)
    val name = MutableLiveData<String>()
    val phoneNum = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val emergencyNum = MutableLiveData<String>()
    val emergencyName = MutableLiveData<String>()
    val opSuccessful = MutableLiveData<Boolean>()

    fun saveUser(){
        val obFormEntity = getPersonData()
        viewModelScope.launch {
            withContext(dispatcher.IO){
                DB3.insertPerson(arrayListOf<FormEntity>(
                    obFormEntity
                ))
            }
        }
    }

    fun getPersonData(): FormEntity {
        return FormEntity(
            id.value ?: 0,
            name.value ?: "",
            phoneNum.value ?: "",
            email.value ?: "",
            emergencyNum.value ?: "",
            emergencyName.value ?: ""
        )
    }

    fun updateUser(){
        val obFormEntity = getPersonData()
        viewModelScope.launch {
            val result = withContext(dispatcher.IO) {
                DB3.updatePerson(obFormEntity)
            }
            opSuccessful.value = (result>0)
        }
    }


    fun loadData(person: FormEntity) {
        viewModelScope.launch {
            id.value = person.id
            name.value = person.fullName
            phoneNum.value = person.phoneNumber
            email.value = person.email
            emergencyNum.value = person.emergencyNumber
            emergencyName.value = person.emergencyName

        }
    }
}
