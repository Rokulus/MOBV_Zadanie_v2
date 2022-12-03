package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.ContactItem

class LocalCache(private val dao: DbDao) {
    suspend fun insertBars(bars: List<BarItem>){
        dao.insertBars(bars)
    }

    suspend fun deleteBars(){ dao.deleteBars() }

    fun getBars(): LiveData<List<BarItem>?> = dao.getBars()

    suspend fun insertContacts(contacts: List<ContactItem>){
        dao.insertContacts(contacts)
    }

    suspend fun deleteContacts(){ dao.deleteContacts() }

    fun getContacts(): LiveData<List<ContactItem>?> = dao.getContacts()
}