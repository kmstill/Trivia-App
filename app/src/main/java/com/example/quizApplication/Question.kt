package com.example.quizApplication

data class Question (
    val id: Int,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: AnswerOptions
){
    override fun toString(): String = question+optionA
}

enum class AnswerOptions{
    A,B,C,D, noneSelected
}