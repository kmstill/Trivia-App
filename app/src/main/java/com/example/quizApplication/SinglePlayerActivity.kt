package com.example.quizApplication

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class SinglePlayerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mUsedIds: HashSet<String>
    private var mNumQuestions = 0
    private var mNumCorrect = 0
    private var mSelectedOption = AnswerOptions.NoneSelected
    private var mCorrectAnswer = AnswerOptions.NoneSelected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)

        //initialize fields
        mDatabase = Firebase.database.reference
        mNumQuestions = intent.getIntExtra(Constants.NUM_QUESTIONS, 10)
        mUsedIds = HashSet()

        //Get XML elements
        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        val tvOptA = findViewById<TextView>(R.id.tv_option_a)
        val tvOptB = findViewById<TextView>(R.id.tv_option_b)
        val tvOptC = findViewById<TextView>(R.id.tv_option_c)
        val tvOptD = findViewById<TextView>(R.id.tv_option_d)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        val pbProgress = findViewById<ProgressBar>(R.id.pb_progress)

        //populate first question
        getQuestion(tvOptA, tvOptB, tvOptC, tvOptD, tvQuestion)
        defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
        pbProgress.max = mNumQuestions
        tvProgress.text = "1/${pbProgress.max}"

        //set onCLick listeners for buttons
        tvOptA.setOnClickListener(this)
        tvOptB.setOnClickListener(this)
        tvOptC.setOnClickListener(this)
        tvOptD.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun getUniqueId(): String {
        var id: Int
        do {
            id = Random.nextInt(Constants.TOTAL_QUESTIONS)
        } while (mUsedIds.contains(id.toString()))
        mUsedIds.add(id.toString())
        return id.toString()
    }

    private fun getQuestion(
        tvOptA: TextView,
        tvOptB: TextView,
        tvOptC: TextView,
        tvOptD: TextView,
        tvQuestion: TextView
    ) {
        val newQuestionId = getUniqueId()
        val questionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val question = dataSnapshot.child("question").getValue(false).toString()
                val optA = dataSnapshot.child("A").getValue(false).toString()
                val optB = dataSnapshot.child("B").getValue(false).toString()
                val optC = dataSnapshot.child("C").getValue(false).toString()
                val optD = dataSnapshot.child("D").getValue(false).toString()
                val ans = dataSnapshot.child("answer").getValue(false).toString()
                tvQuestion.text = question
                tvOptA.text = optA
                tvOptB.text = optB
                tvOptC.text = optC
                tvOptD.text = optD
                mCorrectAnswer = getCorrectAnswer(ans)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        mDatabase.child(newQuestionId).addValueEventListener(questionListener)
    }

    /**
     * Sets all options to default unselected view
     */
    private fun defaultOptionsView(
        context: Context,
        tvOptA: TextView,
        tvOptB: TextView,
        tvOptC: TextView,
        tvOptD: TextView,
        btnSubmit: Button
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
        btnSubmit.text = "Submit"
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

    private fun updateProgressBar(
        pbProgress: ProgressBar,
        tvProgressBar: TextView,
    ) {
        pbProgress.progress += 1
        tvProgressBar.text = "${pbProgress.progress}" + "/" + pbProgress.max

    }

    /**
     * Converts correct answer string read from database to CorrectAnswers enum value
     */
    fun getCorrectAnswer(ansString: String): AnswerOptions {
        var answer = AnswerOptions.NoneSelected
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
     * Makes selected answer red if incorrect
     * Makes correct answer green
     */
    private fun answerView(
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

    override fun onClick(v: View?) {
        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        val tvOptA = findViewById<TextView>(R.id.tv_option_a)
        val tvOptB = findViewById<TextView>(R.id.tv_option_b)
        val tvOptC = findViewById<TextView>(R.id.tv_option_c)
        val tvOptD = findViewById<TextView>(R.id.tv_option_d)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        val pbProgress = findViewById<ProgressBar>(R.id.pb_progress)

        when (v?.id) {
            R.id.tv_option_a -> {
                defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                selectedOptionView(this, tvOptA)
                mSelectedOption = AnswerOptions.A
            }
            R.id.tv_option_b -> {
                defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                selectedOptionView(this, tvOptB)
                mSelectedOption = AnswerOptions.B
            }
            R.id.tv_option_c -> {
                defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                selectedOptionView(this, tvOptC)
                mSelectedOption = AnswerOptions.C
            }
            R.id.tv_option_d -> {
                defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                selectedOptionView(this, tvOptD)
                mSelectedOption = AnswerOptions.D
            }
            R.id.btn_submit -> {
                if (mSelectedOption == AnswerOptions.NoneSelected) {
                    if (pbProgress.progress == mNumQuestions) {
                        val resultIntent = Intent(this, SinglePlayerResultsActivity::class.java)
                        resultIntent.putExtra(
                            Constants.USER_NAME,
                            intent.getStringExtra(Constants.USER_NAME)
                        )
                        resultIntent.putExtra(Constants.NUM_CORRECT, mNumCorrect)
                        resultIntent.putExtra(Constants.NUM_QUESTIONS, mNumQuestions)
                        resultIntent.putExtra(
                            Constants.USER_ID,
                            intent.getStringExtra(Constants.USER_ID)
                        )
                        startActivity(resultIntent)
                    }
                    updateProgressBar(pbProgress, tvProgress)
                    defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                    getQuestion(tvOptA, tvOptB, tvOptC, tvOptD, tvQuestion)
                    return
                }

                defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD, btnSubmit)
                if (mSelectedOption == mCorrectAnswer) {
                    mNumCorrect++
                } else {
                    answerView(
                        this, tvOptA, tvOptB, tvOptC, tvOptD, mSelectedOption,
                        R.drawable.incorrect_option_border_bg
                    )
                }
                answerView(
                    this, tvOptA, tvOptB, tvOptC, tvOptD, mCorrectAnswer,
                    R.drawable.correct_option_border_bg
                )
                if (pbProgress.progress < pbProgress.max) {
                    btnSubmit.text = "Go to Next Question"
                } else {
                    btnSubmit.text = "View Results"
                }
                mSelectedOption = AnswerOptions.NoneSelected
            }
        }
    }

}
