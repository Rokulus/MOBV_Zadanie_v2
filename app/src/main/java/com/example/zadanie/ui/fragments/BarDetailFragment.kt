package com.example.zadanie.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentDetailBarBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class BarDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBarBinding
    private lateinit var viewModel: DetailViewModel
    private val navigationArgs: BarDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        binding = FragmentDetailBarBinding.inflate(inflater, container, false)
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

            bnd.mapButton.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            " " +
                                    "${viewModel.bar.value?.lat ?: 0}," +
                                    "${viewModel.bar.value?.lon ?: 0}" +
                                    "(${viewModel.bar.value?.name ?: ""}"
                        )
                    )
                )
            }

            bnd.websiteButton.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(viewModel.bar.value?.tags?.get("website"))
                    )
                )
            }
        }

        viewModel.loadBar(navigationArgs.id)
    }

}