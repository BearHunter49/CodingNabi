package com.example.codingnabi.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.codingnabi.data.dao.DescriptionDAO
import com.example.codingnabi.data.dao.ProblemDAO
import com.example.codingnabi.data.dao.VideoDAO
import com.example.codingnabi.data.entity.Description
import com.example.codingnabi.data.entity.Problem
import com.example.codingnabi.data.entity.Video

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