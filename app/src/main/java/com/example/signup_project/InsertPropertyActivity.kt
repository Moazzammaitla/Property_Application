package com.example.signup_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.signup_project.databinding.ActivityInsertPropertyBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InsertPropertyActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityInsertPropertyBinding
    private lateinit var db:MyAppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Define arrays for Spinner options
        val areaOptions = arrayOf("Select Area", "3 marla", "5 marla", "20 marla")
        val typeOptions = arrayOf("Select Rent or Sale", "Rent", "Sale")
        val furnishedOptions = arrayOf("Select Furnished or Non-Furnished", "Furnished", "Non-Furnished")

        // Create ArrayAdapter for the Spinners
        val areaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaOptions)
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeOptions)
        val furnishedAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, furnishedOptions)

        // Specify dropdown layout style
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        furnishedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Set the adapters to the Spinners
        binding.areaSpinner.adapter = areaAdapter
        binding.typeSpinner.adapter = typeAdapter
        binding.furnishedSpinner.adapter = furnishedAdapter

        // Handle the "Save" button click
        binding.saveButton.setOnClickListener {
            // Retrieve property details from views using View Binding
            val area = binding.areaSpinner.selectedItem.toString()
            val address = binding.addressEditText.text.toString()
            val city = binding.cityEditText.text.toString()
            val type = binding.typeSpinner.selectedItem.toString()
            val rooms = binding.roomsEditText.text.toString()
            val kitchens = binding.kitchensEditText.text.toString()
            val washrooms = binding.washroomsEditText.text.toString()
            val furnished = binding.furnishedSpinner.selectedItem.toString()

            // Create a PropertyData object
            val propertyData = PropertyData(
                area = area,
                address = address,
                city = city,
                type = type,
                rooms = rooms,
                kitchens = kitchens,
                washrooms = washrooms,
                furnished = furnished
            )
            saveUserDataToDatabase(propertyData)
            Toast.makeText(it.context,"Data Insert Done", Toast.LENGTH_LONG).show()
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)


        }
    }


    private fun saveUserDataToDatabase(propertyData: PropertyData) {
        db = MyAppDatabase.getInstance(this)
        GlobalScope.launch {
            // Insert the user data into the Room database
            db.propertyDataDao().insertProperty(propertyData)
        }
    }

}