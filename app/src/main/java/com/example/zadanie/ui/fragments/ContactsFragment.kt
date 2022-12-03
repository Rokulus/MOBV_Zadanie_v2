package com.example.zadanie.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.app.ActivityCompat
import androidx.core.view.MenuHost
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentContactsBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.ContactsViewModel
import androidx.core.view.MenuProvider



class ContactsFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewmodel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(ContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

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
            model = viewmodel
        }.also { bnd ->

            bnd.swiperefresh.setOnRefreshListener {
                viewmodel.refreshData()
            }
        }

        viewmodel.loading.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = it
        }

        viewmodel.message.observe(viewLifecycleOwner) {
            if (PreferenceData.getInstance().getUserItem(requireContext()) == null) {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_login)
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.bars_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.logoutMenuItem -> {
                PreferenceData.getInstance().clearData(requireContext())
                view?.let { Navigation.findNavController(it).navigate(R.id.action_to_login) }
            }

            R.id.goToContactsMenuItem -> {
                view?.let { Navigation.findNavController(it).navigate(R.id.action_to_contacts) }
            }
        }
        return false
    }

}