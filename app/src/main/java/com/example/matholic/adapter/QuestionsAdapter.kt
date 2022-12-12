package com.example.matholic.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matholic.R
import com.example.matholic.model.Question
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import java.io.File

class QuestionsAdapter : RecyclerView.Adapter<QuestionsAdapter.QuestionHolder>() {
     var questionList = emptyList<Question>()

    class QuestionHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val question :  ImageView
            get() {
                return itemView.findViewById<ImageView>(R.id.image)
            }
        val expression : TextView
            get() {
                return itemView.findViewById(R.id.expression)
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        return QuestionHolder(LayoutInflater.from(parent.context).inflate(R.layout.listitem_expression,parent,false))
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        val item = questionList.get(position)
        val bmp = ImageWorker.from(holder.itemView.context)
            .directory("questions")
            .setFileName(item.uuid)
            .withExtension(Extension.PNG)
            .load()


        Glide.with(holder.itemView.context).load(bmp)
            .dontAnimate()
            .into(holder.question)
        holder.expression.setText(item.expression)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    fun setAllData(questions: List<Question>) {
        this.questionList = questions
        notifyDataSetChanged()
    }
}