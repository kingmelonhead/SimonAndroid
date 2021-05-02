package com.example.simongame.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Query("SELECT * FROM high_score_table ORDER BY high_score")
    suspend fun fetchAllScores(): List<GameObject>

    @Query("SELECT * FROM high_score_table ORDER BY scoreId ASC")
    fun getAllScores(): Flow<List<GameObject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(score: GameObject)




}