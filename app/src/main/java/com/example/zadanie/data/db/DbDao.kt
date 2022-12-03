package com.example.zadanie.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.ContactItem

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBars(bars: List<BarItem>)

    @Query("DELETE FROM bars")
    suspend fun deleteBars()

    @Query("SELECT * FROM bars order by users DESC, name ASC")
    fun getBars(): LiveData<List<BarItem>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: List<ContactItem>)

    @Query("DELETE FROM contacts")
    suspend fun deleteContacts()

    @Query("SELECT * FROM contacts")
    fun getContacts(): LiveData<List<ContactItem>?>
}