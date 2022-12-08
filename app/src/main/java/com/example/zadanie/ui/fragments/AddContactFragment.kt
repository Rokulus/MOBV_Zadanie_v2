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
import androidx.navigation.findNavController
import com.example.zadanie.helpers.Evento
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import kotlinx.coroutines.launch
import com.example.zadanie.ui.viewmodels.AddContactViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddContactFragment() : Fragment() {
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var viewModel: AddContactViewModel

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
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_login)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }.also { bnd ->
            bnd.back.setOnClickListener { it.findNavController().popBackStack() }
        }

        binding.fragment = this
    }

    fun addContact() {
        if(binding.contactUsername.toString().isNotBlank()){
            viewModel.addContact(binding.contactUsername.text.toString())
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.action_to_contacts) }
    }
}