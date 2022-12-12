package com.example.matholic.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.matholic.model.Question

@Dao
interface QuestionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addQuestion(question: Question)

    @Query("SELECT * FROM question_table")
    fun getAllQuestions() : LiveData<List<Question>>

    @Query("UPDATE question_table SET expression = :expression WHERE uuid = :uuid")
    fun updateQuestion(uuid : String, expression : String)
}