package com.example.matholic.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.matholic.model.Question

@Database(entities = [Question::class],version = 1,exportSchema = false)
abstract class QuestionDb : RoomDatabase(){
    abstract fun contactDao():QuestionDAO

    companion object{
        @Volatile
        private var INSTANCE: QuestionDb? = null
        fun getDatabase(context: Context): QuestionDb{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestionDb::class.java,
                    "question_table"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE=instance
                return instance
            }
        }
    }
}