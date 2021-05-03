package com.example.simongame.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "high_score_table")
data class ScoreContainer(
        @ColumnInfo(name = "roundCount")
        val score: Int,
        @ColumnInfo(name = "difficulty")
        val difficulty: String,
        @ColumnInfo(name = "date")
        val date: String,
        @PrimaryKey(autoGenerate = true)
        var id: Int
)


