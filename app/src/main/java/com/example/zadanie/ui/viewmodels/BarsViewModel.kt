package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.Evento
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    var bars: LiveData<List<BarItem>?> =
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            emitSource(repository.dbBars())
        }

    var descName = true;
    var ascCount = true;
    var descType = true;

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}

    fun sortByName(){
        viewModelScope.launch {
            if(descName){
                bars = liveData {
                    emitSource(repository.dbBarsByName())
                }
                descName = false
            } else {
                bars = liveData {
                    emitSource(repository.dbBarsByNameDesc())
                }
                descName=true
            }
        }
        refreshData()
    }

    fun sortByCount(){
        viewModelScope.launch {
            if (ascCount){
                bars = liveData {
                    emitSource(repository.dbBarsByCount())
                }
                ascCount=false
            }else{
                bars = liveData {
                    emitSource(repository.dbBarsByCountAsc())
                }
                ascCount=true
            }
        }
        refreshData()
    }

    fun sortByType(){
        viewModelScope.launch {
            if (descType){
                bars = liveData {
                    emitSource(repository.dbBarsByType())
                }
                descType=false
            }else{
                bars = liveData {
                    emitSource(repository.dbBarsByTypeDesc())
                }
                descType=true
            }
        }
        refreshData()
    }

}