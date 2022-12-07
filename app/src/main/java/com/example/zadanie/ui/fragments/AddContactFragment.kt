package com.example.zadanie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentAddContactBinding
import com.example.zadanie.data.DataRepository
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.zadanie.helpers.Evento
import com.example.zadanie.helpers.Injection
import kotlinx.coroutines.launch
import com.example.zadanie.ui.viewmodels.AddContactViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddContactFragment() : Fragment() {
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var viewModel: AddContactViewModel
//    private val _successAddedContact = MutableLiveData<Evento<Boolean>>()
//    val successAddedContact: LiveData<Evento<Boolean>>
//        get() = _successAddedContact
//    private val _message = MutableLiveData<Evento<String>>()
//    val message: LiveData<Evento<String>>
//        get() = _message

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(AddContactViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
    }

    fun addContact() {
        if(binding.contactUsername.toString().isNotBlank()){
//            lifecycleScope.launch {
//                repository.apiAddContact(
//                    binding.contactUsername.toString(),
//                    {_message.postValue(Evento(binding.contactUsername.toString()))},
//                    {_successAddedContact.postValue(Evento(false))}
//                )
//            }
            viewModel.addContact(binding.contactUsername.text.toString())
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.action_to_contacts) }
    }
}