package com.example.signup_project
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class,PropertyData::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun propertyDataDao(): PropertyDataDao


    companion object{
        private var INSTANCE:MyAppDatabase?=null
        fun getInstance(context: Context): MyAppDatabase {
            if(INSTANCE ==null){
               INSTANCE= Room.databaseBuilder(context.applicationContext,
                    MyAppDatabase::class.java,
                    "myapp_database"
                ).build()
            }
            return INSTANCE!!
        }

    }
}
