package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

class AddContactViewModel(private val repository: DataRepository): ViewModel() {

    private val _successAddedContact = MutableLiveData<Evento<String>>()
    val successAddedContact: LiveData<Evento<String>>
        get() = _successAddedContact
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    fun addContact(username: String) {
        viewModelScope.launch {
            repository.apiAddContact(
                username,
                {_message.postValue(Evento(username))},
                {_successAddedContact.postValue(Evento(username))}
            )
        }
    }
}