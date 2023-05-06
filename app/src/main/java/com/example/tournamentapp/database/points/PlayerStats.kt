package com.example.tournamentapp.database.points

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_points_table")
data class PlayerStats(
    @PrimaryKey(autoGenerate = true)
    val playerId: Int = 0,

    @ColumnInfo
    val playerName: String,

    @ColumnInfo
    val won: Int = 0,

    @ColumnInfo
    val lost: Int = 0,

    @ColumnInfo
    val draws: Int = 0,

    @ColumnInfo
    val points: Int = 0,

    @ColumnInfo
    val tournamentId: Long,

)
