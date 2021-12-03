package com.test.androidassesmenttest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.androidassesmenttest.databinding.RowLocationListBinding
import com.test.androidassesmenttest.model.Result

class LocationListAdapter(context: Context) :
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    private var locations = listOf<Result>()
    private val clickHandler: OnItemSelectedListener = context as OnItemSelectedListener

    interface OnItemSelectedListener {
        fun onLocationItemSelected(data: Result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowLocationListBinding.inflate(inflater, parent, false)

        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.binding.rowTxtName.text = locations[position].name
        holder.binding.root.setOnClickListener { clickHandler.onLocationItemSelected(locations[position]) }
    }

    override fun getItemCount() = locations.size

    /**
     * Update the latest location data to the list view
     * @param locations : Updated location data
     */
    fun setLocationList(locations: List<Result>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    class LocationViewHolder(val binding: RowLocationListBinding) :
        RecyclerView.ViewHolder(binding.root)
}


