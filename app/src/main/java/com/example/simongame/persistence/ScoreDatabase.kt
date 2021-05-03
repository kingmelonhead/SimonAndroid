package com.example.simongame.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [ScoreContainer::class], version = 1)
abstract class ScoreDatabase : RoomDatabase() {
    abstract fun simonDao(): ScoreDAO

    companion object {
        @Volatile
        private var INSTANCE: com.example.simongame.persistence.ScoreDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): com.example.simongame.persistence.ScoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.example.simongame.persistence.ScoreDatabase::class.java,
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