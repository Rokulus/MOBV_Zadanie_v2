package com.example.zadanie.ui.widget.contactlist

import com.example.zadanie.data.db.model.ContactItem

interface ContactsEvents {
    fun onContactClick(contact: ContactItem)
}