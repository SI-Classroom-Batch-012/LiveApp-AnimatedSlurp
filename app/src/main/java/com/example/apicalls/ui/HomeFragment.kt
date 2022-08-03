package com.example.apicalls.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.apicalls.adapter.ItemAdapter
import com.example.apicalls.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    lateinit var imageListAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = binding.imageList

        imageListAdapter = ItemAdapter(requireContext(), emptyList())

        imageList.adapter = imageListAdapter

        viewModel.loading.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorImage.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorImage.visibility = View.GONE
                }
            }
        }

        viewModel.drinks.observe(
            viewLifecycleOwner
        ) {
            imageListAdapter.submitList(it)
        }
    }

    override fun onStop() {
        viewModel.updateDrinks(imageListAdapter.dataset)
        super.onStop()
    }
}
