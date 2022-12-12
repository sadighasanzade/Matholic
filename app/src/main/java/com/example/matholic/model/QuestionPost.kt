package com.example.matholic.model

import java.io.File

data class QuestionPost(
    val uuid: String?,
    val image: File?
) {
    constructor() : this("", null)
}

