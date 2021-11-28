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
import com.test.androidassesmenttest.model.ResponseData
import com.test.androidassesmenttest.service.MainRepository
import com.test.androidassesmenttest.util.hide
import com.test.androidassesmenttest.util.onDone
import com.test.androidassesmenttest.util.show
import com.test.androidassesmenttest.viewmodel.LocationViewModel
import com.test.androidassesmenttest.viewmodel.MyViewModelFactory

class LocationListFragment : Fragment() {

    private lateinit var adapter: LocationListAdapter
    private lateinit var binding: FragmentLocationListBinding
    private lateinit var viewModel: LocationViewModel
    private val retrofitService = ApiManager.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(MainRepository(retrofitService?.retrofitService))
        ).get(LocationViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        adapter = LocationListAdapter(requireContext())
        binding.apply {
            recyclerview.adapter = adapter
            listSearchLayout.editText?.apply {
                requestFocus()
                onDone {
                    if (!listSearchLayout.editText?.text.isNullOrEmpty())
                        viewModel.getLocationList(listSearchLayout.editText?.text.toString().trim())
                }
            }
        }
    }

    //view model
    private fun setupViewModel() {
        viewModel.apply {
            locationList.observe(viewLifecycleOwner, renderLocation)
            errorMessage.observe(viewLifecycleOwner, onErrorObserver)
            isViewLoading.observe(viewLifecycleOwner, isViewLoadingObserver)
            isEmptyList.observe(viewLifecycleOwner, emptyListObserver)
        }
    }

    //observers
    private val renderLocation = Observer<ResponseData> {
        adapter.setLocationList(it.results)
        binding.layoutError.root.hide()
        binding.layoutEmpty.root.hide()
        binding.recyclerview.show()
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility = if (it) View.VISIBLE else View.GONE
        binding.progressBar.visibility = visibility
    }

    private val onErrorObserver = Observer<Any> {
        binding.layoutError.root.show()
        binding.recyclerview.hide()
        binding.layoutError.textViewError.text = "Error $it"
    }

    private val emptyListObserver = Observer<Boolean> {
        binding.layoutEmpty.root.show()
        binding.layoutError.root.hide()
        binding.recyclerview.hide()
    }
}