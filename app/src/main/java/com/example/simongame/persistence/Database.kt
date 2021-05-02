package com.example.simongame.persistence

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [GameObject::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun simonDao(): DAO

    companion object {
        @Volatile
        private var INSTANCE: com.example.simongame.persistence.Database? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): com.example.simongame.persistence.Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.simongame.persistence.Database::class.java,
                        "score_database"
                )
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}