package com.example.matholic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "uuid") var uuid: String,

    @ColumnInfo(name = "expression") var expression : String?,

    @ColumnInfo(name = "image") var image : String?
)
