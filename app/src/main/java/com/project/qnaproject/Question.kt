package com.project.qnaproject

data class Question(
    val __v: Int,
    val _id: String,
    val answers: List<Answer>,
    val question: String
)