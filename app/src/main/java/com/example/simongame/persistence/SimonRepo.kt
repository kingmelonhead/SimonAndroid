package com.example.simongame.persistence

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SimonRepo (private val simonScoreDao: ScoreDAO) {
    val scores: Flow<List<ScoreContainer>> = simonScoreDao.getAllScores()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(score: ScoreContainer) {
        simonScoreDao.insert(score)
    }
    suspend fun fetchScores(): List<ScoreContainer> {
        return simonScoreDao.fetchAllScores()
    }
}