package com.example.simongame.persistence

import android.app.Activity
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Room
import kotlinx.coroutines.flow.Flow

class Repo (private val simonDao: DAO) {
    val scores: Flow<List<GameObject>> = simonDao.getAllScores()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(score: GameObject) {
        simonDao.insert(score)
    }

    suspend fun fetchScores(): List<GameObject> {
        return simonDao.fetchAllScores()
    }
}