package com.example.signup_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.signup_project.databinding.ActivityEditDataBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditDataBinding
    private lateinit var database: MyAppDatabase
    private var propertyId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Room database instance
        database = MyAppDatabase.getInstance(this)

        // Retrieve the property ID passed from the previous activity
        propertyId = intent.getLongExtra("property_id", -1)

        // Fetch the property data based on the property ID
        GlobalScope.launch(Dispatchers.IO) {
            val property = database.propertyDataDao().getPropertyById(propertyId)

            if (property != null) {
                runOnUiThread {
                    // Initialize and populate the spinners
                    initSpinners()

                    // Populate the UI fields with the property data for editing
                    populateUI(property)

                    // Set an OnClickListener on the "Save" button
                    binding.saveButton.setOnClickListener {
                        saveEditedData(property)

                        // Navigate to the ProfileActivity after saving
                        val intent = Intent(this@EditDataActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        finish() // Optional: Finish the EditDataActivity
                    }
                }
            } else {
                // Handle the case where the property with the given ID was not found
                // You can display an error message or navigate back to the previous activity
            }
        }
    }

    private fun initSpinners() {
        // Initialize and populate the Area spinner
        val areaData = listOf("3", "5", "10", "20") // Replace with your own data
        var areaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaData)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.areaSpinner.adapter = areaAdapter

        // Initialize and populate the Type spinner
        val typeData = listOf("Rent", "Sale") // Replace with your own data
        var typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeData)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = typeAdapter

        // Initialize and populate the Furnished spinner
        val furnishedData = listOf("Furnished", "Non-Furnished") // Replace with your own data
        var furnishedAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, furnishedData)
        furnishedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.furnishedSpinner.adapter = furnishedAdapter

        // Set item selection listeners for spinners
        binding.areaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle area selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when nothing is selected
            }
        }

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle type selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when nothing is selected
            }
        }

        binding.furnishedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Handle furnished selection
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle when nothing is selected
            }
        }
    }

    private fun populateUI(property: PropertyData) {
        // Populate the UI fields with the property data
        binding.areaSpinner.setSelection(getAreaIndex(property.area))
        binding.addressEditText.setText(property.address)
        binding.cityEditText.setText(property.city)
        binding.typeSpinner.setSelection(getTypeIndex(property.type))
        binding.roomsEditText.setText(property.rooms.toString())
        binding.kitchensEditText.setText(property.kitchens.toString())
        binding.washroomsEditText.setText(property.washrooms.toString())
        binding.furnishedSpinner.setSelection(getFurnishedIndex(property.furnished))
    }

    private fun saveEditedData(property: PropertyData) {
        // Get the selected items from spinners
        val updatedArea = binding.areaSpinner.selectedItem.toString()
        val updatedAddress = binding.addressEditText.text.toString()
        val updatedCity = binding.cityEditText.text.toString()
        val updatedType = binding.typeSpinner.selectedItem.toString()
        val updatedRooms = binding.roomsEditText.text.toString()
        val updatedKitchens = binding.kitchensEditText.text.toString()
        val updatedWashrooms = binding.washroomsEditText.text.toString()
        val updatedFurnished = binding.furnishedSpinner.selectedItem.toString()

        // Update the property data
        property.area = updatedArea
        property.address = updatedAddress
        property.city = updatedCity
        property.type = updatedType
        property.rooms = updatedRooms
        property.kitchens = updatedKitchens
        property.washrooms = updatedWashrooms
        property.furnished = updatedFurnished

        // Update the property data in the database
        GlobalScope.launch(Dispatchers.IO) {
            database.propertyDataDao().updateProperty(property)

            // Finish the activity to return to the previous screen
            finish()
        }
    }

    private fun getAreaIndex(area: String): Int {
        val areaData = binding.areaSpinner.adapter as ArrayAdapter<String>
        return areaData.getPosition(area)
    }

    private fun getTypeIndex(type: String): Int {
        val typeData = binding.typeSpinner.adapter as ArrayAdapter<String>
        return typeData.getPosition(type)
    }

    private fun getFurnishedIndex(furnished: String): Int {
        val furnishedData = binding.furnishedSpinner.adapter as ArrayAdapter<String>
        return furnishedData.getPosition(furnished)
    }
}