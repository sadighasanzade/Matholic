package com.example.matholic.Utility

import android.content.Context
import com.example.matholic.MainActivity

class Constants {
    companion object{
        var BASE_URL =  "http://${Preferences.get<String>(Preferences.IP, "127.0.0.1:8080")}/"
        val REQUEST_CAMERA = 3169
        val QUESTION_DIR = "questions"
        val IMAGE_KEY = "image_key"
        val KEY_EXP = "expression_key"
    }
}