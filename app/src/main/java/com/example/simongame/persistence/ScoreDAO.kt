package com.example.simongame.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDAO {
    @Query("SELECT * FROM high_score_table ORDER BY roundCount")
    suspend fun fetchAllScores(): List<ScoreContainer>
    @Query("SELECT * FROM high_score_table ORDER BY roundCount DESC")
    fun getAllScores(): Flow<List<ScoreContainer>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: ScoreContainer)
}