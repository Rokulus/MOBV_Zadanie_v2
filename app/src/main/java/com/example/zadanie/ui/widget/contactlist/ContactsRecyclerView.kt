package com.example.zadanie.ui.widget.contactlist

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zadanie.data.db.model.ContactItem
import com.example.zadanie.ui.fragments.ContactsFragmentDirections

class ContactsRecyclerView : RecyclerView {
    private lateinit var contactsAdapter: ContactsAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        contactsAdapter = ContactsAdapter(object : ContactsEvents {
            override fun onContactClick(contact: ContactItem) {
                var bar = "" + contact.barId
                if(contact.barId != null) {
                    this@ContactsRecyclerView.findNavController().navigate(
                        ContactsFragmentDirections.actionToDetail(bar)
                    )
                } else {
                    this@ContactsRecyclerView.findNavController().navigate(
                        ContactsFragmentDirections.actionToBars()
                    )
                }
            }
        })
        adapter = contactsAdapter
    }
}

@BindingAdapter(value = ["contactItems"])
fun ContactsRecyclerView.applyItems(
    contacts: List<ContactItem>?
) {
    (adapter as ContactsAdapter).items = contacts ?: emptyList()
}