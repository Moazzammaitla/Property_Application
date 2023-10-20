package com.example.signup_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.signup_project.databinding.ActivityViewPropertyBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewPropertyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPropertyBinding
    private lateinit var propertyAdapter: PropertyAdapter
    private lateinit var database: MyAppDatabase

    private lateinit var searchEditText: EditText
    private lateinit var areaSpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var typeSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Room database instance
        database = MyAppDatabase.getInstance(this)

        // Initialize RecyclerView and its adapter
        val propertyRecyclerView: RecyclerView = binding.propertyRecyclerView
        propertyRecyclerView.layoutManager = LinearLayoutManager(this)
        propertyAdapter = PropertyAdapter()
        propertyRecyclerView.adapter = propertyAdapter

        // Initialize filter UI elements
        searchEditText = binding.searchBar
        areaSpinner = binding.areaSpinner
        citySpinner = binding.citySpinner
        typeSpinner = binding.typeSpinner

        // Initialize and populate the Spinners
        initSpinners()

        // Set up a TextWatcher for the search bar (EditText)
        searchEditText.addTextChangedListener { text ->
            val searchText = text.toString().trim()
            updatePropertyList(searchText)
        }

        // Set item selection listeners for the Spinners
        areaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedArea = parent?.getItemAtPosition(position).toString()
                updatePropertyList(searchEditText.text.toString().trim(), selectedArea)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = parent?.getItemAtPosition(position).toString()
                updatePropertyList(searchEditText.text.toString().trim(), areaSpinner.selectedItem.toString(), selectedCity)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                updatePropertyList(searchEditText.text.toString().trim(), areaSpinner.selectedItem.toString(), citySpinner.selectedItem.toString(), selectedType)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Fetch all property data from the Room database and update the RecyclerView
        GlobalScope.launch(Dispatchers.IO) {
            val data = database.propertyDataDao().getAllProperties()

            runOnUiThread {
                propertyAdapter.submitList(data)
            }
        }
    }

    private fun initSpinners() {
        // Initialize and populate the Area spinner
        val areaData = listOf("3 marla", "5 marla", "20 marla", "All") // Replace with your own data
        val areaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaData)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaSpinner.adapter = areaAdapter

        // Initialize and populate the City spinner
        val cityData = listOf("Lahore", "Karachi", "Multan", "All") // Replace with your own data
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cityData)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter

        // Initialize and populate the Type spinner
        val typeData = listOf("Rent", "Sale", "All") // Replace with your own data
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeData)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = typeAdapter
    }

    private fun updatePropertyList(
        searchQuery: String = "",
        selectedArea: String = areaSpinner.selectedItem.toString(),
        selectedCity: String = citySpinner.selectedItem.toString(),
        selectedType: String = typeSpinner.selectedItem.toString()
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val properties = database.propertyDataDao().filterProperties(searchQuery, selectedArea, selectedCity, selectedType)

            runOnUiThread {
                propertyAdapter.submitList(properties)
            }
        }
    }
}