package com.example.signup_project

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val phone: String,
    val email: String,
    val password: String
)



















//
//
//@Entity(tableName = "user")
//data class User(
//    @PrimaryKey(autoGenerate = true) val id: Long?,
//    @ColumnInfo(name = "name") val name: String?,
//    @ColumnInfo(name = "phone") val phone: String?,
//    @ColumnInfo(name = "email")  val email: String?,
//    @ColumnInfo(name = "password")  val password: String
//
//
//)