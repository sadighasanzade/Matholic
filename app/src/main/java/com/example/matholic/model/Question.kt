package com.example.matholic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey(autoGenerate = false)
    val uuid: String,
    val expression : String?,
    val image : String?
)
