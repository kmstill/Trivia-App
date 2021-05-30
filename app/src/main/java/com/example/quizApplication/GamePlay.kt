package com.example.quizApplication
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random


object GamePlayFunctions {


    /**
     * Queries firebase realtime data base to fill arrayList with random questions.
     */
    fun getQuestions(numQuestions: Int): ArrayList<Question> {
        val numTotalQuestions =
            540 //look into changing this to dataBase.size() or something rather than hardcoding
        val usedQuestionIds = HashSet<Int>()
        val questionsList = ArrayList<Question>()
        for (i in 0..numQuestions) {
            val questionId = getUniqueId(usedQuestionIds, numTotalQuestions)
           // val newQuestion = getQ(questionId)
            //Log.d("deb", newQuestion.question)
            //questionsList.add(i, newQuestion)
        }
        return questionsList
    }

    private fun getUniqueId(usedIds: HashSet<Int>, upperLimit: Int): Int {
        var id: Int
        do {
            id = Random.nextInt(upperLimit)
        } while (usedIds.contains(id))
        usedIds.add(id)
        return id
    }

    /**
     *
     */
    fun getQ(questionId: Int) {
        val dataBase = Firebase.database.reference
        dataBase.get().addOnSuccessListener {
            Log.d("tag", "fuck Got value ${it.value}")
        }.addOnFailureListener{
            Log.d("tag", "fuck Error getting data", it)
        }
    }

    fun getQValEv(questionId: Int) {
        //val newQuestionList: ArrayList<Question> = ArrayList()
        val dataBase = Firebase.database.reference
        Log.d("fuck", "fuck: $dataBase")
        val questionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.w("fuck", "in ondatachange")
                val question = dataSnapshot.child("10").getValue(false).toString()
                Log.w("fuck", "$question")
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("fuck", "in onCancelled")
            }
        }
        dataBase.addValueEventListener(questionListener)
    }


    /**
     * Converts correct answer string read from database to CorrectAnswers enum value
     */
    fun getCorrectAnswer(ansString: String): AnswerOptions {
        var answer = AnswerOptions.noneSelected
        when (ansString) {
            "A" -> {
                answer = AnswerOptions.A
            }
            "B" -> {
                answer = AnswerOptions.B
            }
            "C" -> {
                answer = AnswerOptions.C
            }
            "D" -> {
                answer = AnswerOptions.D
            }
        }
        return answer
    }


    /**
     * Adds selected view to option that has been selected
     */
    fun selectedOptionView(context: Context, tv: TextView) {
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            context,
            R.drawable.selected_option_border_bg
        )
    }

    /**
     * Sets all options to default unselected view
     */
    fun defaultOptionsView(
        context: Context,
        tvOptA: TextView,
        tvOptB: TextView,
        tvOptC: TextView,
        tvOptD: TextView,
    ) {
        val options = ArrayList<TextView>()
        options.add(0, tvOptA)
        options.add(1, tvOptB)
        options.add(2, tvOptC)
        options.add(3, tvOptD)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background =
                ContextCompat.getDrawable(context, R.drawable.default_option_border_bg)
        }
    }

    /**
     *
     */
    fun answerView(
        context: Context,
        tvOptA: TextView,
        tvOptB: TextView,
        tvOptC: TextView,
        tvOptD: TextView,
        answer: AnswerOptions,
        drawableView: Int
    ) {
        when (answer) {
            AnswerOptions.A -> {
                tvOptA.background = ContextCompat.getDrawable(
                    context, drawableView
                )
            }
            AnswerOptions.B -> {
                tvOptB.background = ContextCompat.getDrawable(
                    context, drawableView
                )
            }
            AnswerOptions.C -> {
                tvOptC.background = ContextCompat.getDrawable(
                    context, drawableView
                )
            }
            AnswerOptions.D -> {
                tvOptD.background = ContextCompat.getDrawable(
                    context, drawableView
                )
            }
        }
    }

    /**
     *
     */
    fun updateProgressBar(
        tvProgressBar: TextView,
        pbProgress: ProgressBar,
        currentProgress: Int
    ) {
        pbProgress.progress = currentProgress
        tvProgressBar.text = "$currentProgress" + "/" + pbProgress.max
    }

    fun setQuestion(
        question: Question,
        tvOptA: TextView,
        tvOptB: TextView,
        tvOptC: TextView,
        tvOptD: TextView,
        tvQuestion: TextView
    ) {
        tvQuestion.text = question.question
        tvOptA.text = question.optionA
        tvOptB.text = question.optionB
        tvOptC.text = question.optionC
        tvOptD.text = question.optionD
    }
}

fun updateProgressBar(
    pbProgress: ProgressBar,
    tvProgressBar: TextView,
    currentProgress: Int,
    totalQuestions: Int
) {
    pbProgress.progress = currentProgress
    tvProgressBar.text = "$currentProgress" + "/" + pbProgress.max

}


object Constants {
    const val P1_NAME: String = "P1_name"
    const val NUM_QUESTIONS: String = "num_questions"
    const val P1_NUM_CORRECT_ANSWERS: String = "P1_num_correct_answers"
}

