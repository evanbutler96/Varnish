package com.example.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.database.ui.theme.project.Project
import com.example.database.ui.theme.project.VarnishDao

// Required arguments so Room can build the database
@Database(entities = [Project::class], version = 3, exportSchema = false)
abstract class VarnishDatabase : RoomDatabase(){

    abstract fun varnishDao(): VarnishDao

    companion object {
        // The Instance variable keeps a reference to the database, when one has been created.
        @Volatile
        private var Instance: VarnishDatabase? = null

        fun getDatabase(context: Context): VarnishDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, VarnishDatabase::class.java, "app_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it}
            }

        }
    }
}