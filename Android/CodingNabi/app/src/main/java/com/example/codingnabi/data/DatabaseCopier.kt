package com.example.codingnabi.data

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import androidx.room.Room
import com.example.codingnabi.utils.CodingNabiApplication
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object DatabaseCopier {
    private const val DATABASE_NAME = "codingnabi.db"
    private var instance: AppDatabase? = null
    private lateinit var version: String

    fun getInstance(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: buildDatabase(context)
        }
    }

    fun downloadLocalDatabase(context: Context){
        Timber.i("downloadLocalDatabase 실행")

        val path = context.getDatabasePath(DATABASE_NAME)
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        version = PackageInfoCompat.getLongVersionCode(info).toString()

        // DB File not exists
        if (!path.exists()){
            // Make directory
            path.parentFile?.mkdirs() ?: Timber.e("There is no ParentFile of $path")
        }

        // DB File not exists
        copyDatabaseFile(context, path)
    }

    private fun buildDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(AppDatabase.MIGRATION_1_2).build()


    private fun copyDatabaseFile(context: Context, path: File?) {
        Timber.i("Database File 복사 시작")

        try {
            val inputStream = context.assets.open("databases/$DATABASE_NAME")
            val output = FileOutputStream(path)

            val bufferSize = 8192
            val buffer = ByteArray(bufferSize)

            // Copy loop
            while (true){
                val length = inputStream.read(buffer, 0, bufferSize)
                if (length <= 0) break
                output.write(buffer, 0, length)
            }

            output.flush()
            output.close()
            inputStream.close()
        }catch (e: IOException){
            Timber.e("Database File Copy Error!")
        }
    }

}