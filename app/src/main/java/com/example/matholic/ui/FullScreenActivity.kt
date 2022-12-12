package com.example.matholic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.matholic.R
import com.example.matholic.Utility.Constants.Companion.IMAGE_KEY
import com.example.matholic.Utility.Constants.Companion.KEY_EXP
import com.example.matholic.Utility.Constants.Companion.QUESTION_DIR
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        val uuid = intent.getStringExtra(IMAGE_KEY)
        val exp = intent.getStringExtra(KEY_EXP)

        val bitmap = ImageWorker.from(this)
            .directory(QUESTION_DIR)
            .setFileName(uuid)
            .withExtension(Extension.PNG)
            .load()

        findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
        findViewById<TextView>(R.id.expression).setText(exp)
    }
}