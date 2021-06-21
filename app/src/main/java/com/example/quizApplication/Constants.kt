package com.example.quizApplication

object Constants {
    const val USER_ID:  String = "user_id"
    const val USER_NAME: String = "user_name"
    const val NUM_QUESTIONS: String = "num_questions"
    const val TOTAL_QUESTIONS: Int = 500
    const val NUM_CORRECT: String = "num_correct"
}

enum class AnswerOptions {
    A, B, C, D, NoneSelected
}

data class UserStats (
    val gamesPlayed:Int = 0,
    val totalQuestions:Int = 0,
    val correctAnswers:Int = 0,
    )