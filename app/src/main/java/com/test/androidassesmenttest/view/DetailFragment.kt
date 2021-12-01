package com.test.androidassesmenttest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.test.androidassesmenttest.R
import com.test.androidassesmenttest.databinding.FragmentLocationDetailBinding
import com.test.androidassesmenttest.databinding.FragmentLocationListBinding
import com.test.androidassesmenttest.model.Result
import com.test.androidassesmenttest.util.AppConstant
import com.test.androidassesmenttest.util.putArgs

class DetailFragment : Fragment() {
    companion object {
        fun newInstance(result: Result) = DetailFragment().putArgs {
            putParcelable(AppConstant.KEY_RESULT, result)
        }
    }
    private lateinit var binding: FragmentLocationDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = requireArguments().getParcelable<Result>(AppConstant.KEY_RESULT)
        result?.let { setData(it) }
    }

    private fun setData(data: Result) {
        binding.apply {
            detailTxtName.text = data.name
            detailTxtDesc.text = data.location.run {
                address.plus("\n").plus(locality).plus("\n")
                    .plus(country).plus("\n").plus(region).plus("\n")
                    .plus(postcode)
            }
        }
    }

}