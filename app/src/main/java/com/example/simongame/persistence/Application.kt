package com.example.simongame.persistence

import android.app.Application
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.simongame.persistence.DAO
import com.example.simongame.persistence.Database
import com.example.simongame.persistence.GameObject
import com.example.simongame.persistence.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class Application: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { Database.getDatabase(this, applicationScope)}
    val repository by lazy { Repo(database.simonDao()) }




}