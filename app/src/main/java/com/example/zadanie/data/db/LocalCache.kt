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

    fun getBarsByName(): LiveData<List<BarItem>?> = dao.getBarsByName()

    fun getBarsByCount(): LiveData<List<BarItem>?> = dao.getBarsByCount()

    fun getBarsByType(): LiveData<List<BarItem>?> = dao.getBarsByType()

    fun getBarsByNameDesc(): LiveData<List<BarItem>?> = dao.getBarsByNameDesc()

    fun getBarsByCountAsc(): LiveData<List<BarItem>?> = dao.getBarsByCountAsc()

    fun getBarsByTypeDesc(): LiveData<List<BarItem>?> = dao.getBarsByTypeDesc()

    suspend fun insertContacts(contacts: List<ContactItem>){
        dao.insertContacts(contacts)
    }

    suspend fun deleteContacts(){ dao.deleteContacts() }

    fun getContacts(): LiveData<List<ContactItem>?> = dao.getContacts()
}