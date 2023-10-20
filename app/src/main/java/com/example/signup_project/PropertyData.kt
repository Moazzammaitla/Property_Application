package com.example.signup_project

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "propertydata")
data class PropertyData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var area: String,
    var address: String,
    var city: String,
    var type: String,
    var rooms: String,
    var kitchens: String,
    var washrooms: String,
    var furnished: String
)
