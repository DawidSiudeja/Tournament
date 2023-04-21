package com.example.tournamentapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournament_table")
data class Tournaments(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo val title: String?,
    @ColumnInfo val date: String?,
    @ColumnInfo val winner: String? = "None",
    @ColumnInfo val players: String?,
    @ColumnInfo val gameType: String,
    @ColumnInfo val isFinished: Boolean
)