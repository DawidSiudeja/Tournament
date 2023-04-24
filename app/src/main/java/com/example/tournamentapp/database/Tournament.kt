package com.example.tournamentapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime



@Entity(tableName = "tournament_table")
data class Tournament(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val date: String = LocalDateTime.now().toString(),

    @ColumnInfo
    val winner: String = "None",

    @ColumnInfo
    val players: String,

    @ColumnInfo
    val gameType: String,

    @ColumnInfo
    val isFinished: Boolean = false,


    )