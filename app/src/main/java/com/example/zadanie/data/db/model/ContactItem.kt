package com.example.zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

//data class ContactItem(
//    @Json(name = "user_id")
//    val userId: String,
//    @Json(name = "user_name")
//    val username: String,
//    @Json(name = "bar_id")
//    val barId: String? = null,
//    @Json(name = "bar_name")
//    val barName: String? = null,
//    val time: String? = null,
//    @Json(name = "bar_lat")
//    val barLat: String? = null,
//    @Json(name = "bar_lon")
//    val barLon: String? = null
//)

@Entity(tableName = "contacts")
class ContactItem (
//    @PrimaryKey val id: String,
    @PrimaryKey val userId: String,
    val username: String,
    val barId: String? = null,
    val barName: String? = null,
    val time: String? = null,
    val barLat: Double? = null,
    val barLon: Double? = null
){
    override fun toString(): String {
        return "ContactItem(userId='$userId', username='$username', barId='$barId', barName='$barName', time='$time', barLat=$barLat, barLon=$barLon)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactItem) return false

        if (userId != other.userId) return false
        if (username != other.username) return false
        if (barId != other.barId) return false
        if (barName != other.barName) return false
        if (time != other.time) return false
        if (barLat != other.barLat) return false
        if (barLon != other.barLon) return false

        return true
    }
}