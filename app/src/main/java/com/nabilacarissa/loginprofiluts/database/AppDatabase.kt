package com.nabilacarissa.loginprofiluts.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nabilacarissa.loginprofiluts.dao.UserDao
import com.nabilacarissa.loginprofiluts.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myapp_database.db"
                ).build()

                // Log path database ke Logcat
                val dbFile = context.getDatabasePath("myapp_database.db")
                Log.d("DB_PATH", "Database stored at: ${dbFile.absolutePath}")

                INSTANCE = instance
                instance
            }
        }
    }
}
