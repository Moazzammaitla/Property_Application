package com.example.signup_project

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PropertyDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProperty(propertyData: PropertyData)

    @Query("SELECT * FROM propertydata")
    suspend fun getAllProperties(): List<PropertyData>

    @Query("SELECT * FROM propertydata WHERE id = :propertyId")
    suspend fun getPropertyById(propertyId: Long): PropertyData?

    @Update
    suspend fun updateProperty(propertyData: PropertyData)

    @Delete
    suspend fun deleteProperty(propertyData: PropertyData)


    @Query("SELECT * FROM propertyData WHERE " +
            "(:searchQuery = '' OR address LIKE '%' || :searchQuery || '%') AND " +
            "(:selectedArea = 'All' OR area = :selectedArea) AND " +
            "(:selectedCity = 'All' OR city = :selectedCity) AND " +
            "(:selectedType = 'All' OR type = :selectedType)")
    suspend fun filterProperties(
        searchQuery: String,
        selectedArea: String,
        selectedCity: String,
        selectedType: String
    ): List<PropertyData>


}
