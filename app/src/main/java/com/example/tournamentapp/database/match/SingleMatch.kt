package com.example.tournamentapp.database.match

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "single_match_table")
data class SingleMatch(

    @PrimaryKey(autoGenerate = true)
    val matchId: Int = 0,

    @ColumnInfo
    val player1: String,

    @ColumnInfo
    val player2: String,

    @ColumnInfo
    val player1Score: Int = -1,

    @ColumnInfo
    val player2Score: Int = -1,

    @ColumnInfo
    val isFinished: Boolean = false,

    @ColumnInfo
    val tournamentId: Int,
)
