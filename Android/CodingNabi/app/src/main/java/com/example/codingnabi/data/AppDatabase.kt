package com.example.codingnabi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Problem::class, Description::class, Video::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun problemDAO(): ProblemDAO
    abstract fun descriptionDAO(): DescriptionDAO
    abstract fun videoDAO(): VideoDAO

    companion object {  // static method
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Nothing was changed in schema.
                // It just for migration db file into this device.
            }
        }
    }
}