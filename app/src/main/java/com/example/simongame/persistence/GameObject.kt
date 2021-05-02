package com.example.simongame.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity(tableName = "high_score_table")
data class GameObject(
        @PrimaryKey(autoGenerate = true)
        var scoreId: Int,

        @ColumnInfo(name = "high_score")
        val highScore: Int,

        @ColumnInfo(name = "difficulty")
        val difficulty: String,

        @ColumnInfo(name = "date")
        val date: String
)


