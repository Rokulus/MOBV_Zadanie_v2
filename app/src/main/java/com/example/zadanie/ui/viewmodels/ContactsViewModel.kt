package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.ContactItem
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val contacts: LiveData<List<ContactItem>?> =
        liveData {
            loading.postValue(true)
            repository.apiContactList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            emitSource(repository.dbContacts())
        }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiContactList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}
}