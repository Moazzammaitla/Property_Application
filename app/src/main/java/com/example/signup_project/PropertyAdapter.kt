package com.example.signup_project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.signup_project.databinding.ItemPropertyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyAdapter: ListAdapter<PropertyData, PropertyAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = getItem(position)
        holder.bind(property)
    }

    class PropertyViewHolder(val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root) {
        private val areaTextView: TextView = binding.areaTextView
        private val addressTextView: TextView = binding.addressTextView
        private val cityTextView: TextView = binding.cityTextView
        private val typeTextView: TextView = binding.typeTextView
        private val roomsTextView: TextView = binding.roomsTextView
        private val kitchensTextView: TextView = binding.kitchensTextView
        private val washroomsTextView: TextView = binding.washroomsTextView
        private val furnishedTextView: TextView = binding.furnishedTextView

        fun bind(property: PropertyData) {
            areaTextView.text = "Area: ${property.area}"
            addressTextView.text = "Address: ${property.address}"
            cityTextView.text = "City: ${property.city}"
            typeTextView.text = "Type: ${property.type}"
            roomsTextView.text = "Rooms: ${property.rooms}"
            kitchensTextView.text = "Kitchens: ${property.kitchens}"
            washroomsTextView.text = "Washrooms: ${property.washrooms}"
            furnishedTextView.text = "Furnished: ${property.furnished}"

            // Set an OnClickListener on the "Edit" button
            val editButton: Button = binding.editButton
            editButton.setOnClickListener {
                // Navigate to EditDataActivity and pass the property ID
                val intent = Intent(it.context, EditDataActivity::class.java)
                intent.putExtra("property_id", property.id)
                it.context.startActivity(intent)
            }

            // Set an OnClickListener on the "Delete" button
            val deleteButton: Button = binding.deleteButton
            deleteButton.setOnClickListener {
                // Delete the property data from the database
                CoroutineScope(Dispatchers.IO).launch {
                    MyAppDatabase.getInstance(it.context).propertyDataDao().deleteProperty(property)
                }

                // Show a toast message indicating successful deletion
                Toast.makeText(it.context, "Property deleted", Toast.LENGTH_SHORT).show()

                // You can decide whether to notify the adapter and update the UI here
            }
        }
    }
}

class PropertyDiffCallback : DiffUtil.ItemCallback<PropertyData>() {
    override fun areItemsTheSame(oldItem: PropertyData, newItem: PropertyData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PropertyData, newItem: PropertyData): Boolean {
        return oldItem == newItem
    }
}