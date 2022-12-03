package com.example.zadanie.ui.widget.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.R
import com.example.zadanie.data.db.model.ContactItem
import com.example.zadanie.helpers.autoNotify
import kotlin.properties.Delegates

class ContactsAdapter(val events: ContactsEvents? = null) :
    RecyclerView.Adapter<ContactsAdapter.ContactItemViewHolder>() {
    var items: List<ContactItem> by Delegates.observable(emptyList()) {_, old, new ->
        autoNotify(old, new) { o, n -> o.userId.compareTo(n.userId) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactItemViewHolder {
        return ContactItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(items[position], events)
    }

    class ContactItemViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.contact_item,
            parent,
            false)
        ) : RecyclerView.ViewHolder(itemView) {

            fun bind(item: ContactItem, events: ContactsEvents?) {
                itemView.findViewById<TextView>(R.id.name).text = item.username
                if(item.barName != null){
                    itemView.findViewById<TextView>(R.id.current_bar).text = "Is in: " + item.barName
                } else {
                    itemView.findViewById<TextView>(R.id.current_bar).text = item.barName
                }
                itemView.findViewById<TextView>(R.id.contact_id).text = "User ID:" + item.userId

                itemView.setOnClickListener { events?.onContactClick(item) }
            }
        }
}