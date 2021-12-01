package com.test.androidassesmenttest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.androidassesmenttest.client.ApiManager
import com.test.androidassesmenttest.databinding.FragmentLocationListBinding
import com.test.androidassesmenttest.model.Resource
import com.test.androidassesmenttest.model.ResponseData
import com.test.androidassesmenttest.model.Status
import com.test.androidassesmenttest.service.LocationRepository
import com.test.androidassesmenttest.util.hide
import com.test.androidassesmenttest.util.onDone
import com.test.androidassesmenttest.util.show
import com.test.androidassesmenttest.viewmodel.LocationViewModel
import com.test.androidassesmenttest.viewmodel.LocationViewModelFactory

class LocationListFragment : Fragment() {

    private lateinit var adapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var retrofitService: ApiManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        retrofitService = ApiManager.instance
        viewModel = ViewModelProvider(
            this,
            LocationViewModelFactory(
                LocationRepository(retrofitService.retrofitService))
        ).get(LocationViewModel::class.java)
    }

    private fun setupUI() {
        adapter = LocationListAdapter(requireContext())
        binding.apply {
            recyclerview.adapter = adapter
            listSearchLayout.editText?.apply {
                requestFocus()
                onDone {
                    if (!listSearchLayout.editText?.text.isNullOrEmpty()) {
                        viewModel.getLocations(listSearchLayout.editText?.text.toString().trim())
                            .observe(viewLifecycleOwner, location)
                    }
                }
            }
        }
    }

    private val location = Observer<Resource<ResponseData?>> {
        it?.let { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.results?.let { results ->
                        binding.progressBar.hide()
                        binding.layoutError.root.hide()
                        if (results.isEmpty()) {
                            binding.recyclerview.hide()
                            binding.layoutEmpty.root.show()
                        } else {
                            binding.recyclerview.show()
                            binding.layoutEmpty.root.hide()
                            adapter.setLocationList(results)
                        }
                    }
                }
                Status.ERROR -> {
                    binding.layoutError.textViewError.text = it.message
                    binding.layoutError.root.show()
                    binding.progressBar.hide()
                    binding.recyclerview.hide()
                    binding.layoutEmpty.root.hide()
                }
                Status.LOADING -> {
                    binding.progressBar.show()
                    binding.layoutError.root.hide()
                    binding.recyclerview.hide()
                    binding.layoutEmpty.root.hide()
                }
            }
        }
    }
}