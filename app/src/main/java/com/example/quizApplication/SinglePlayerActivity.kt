package com.example.quizApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class SinglePlayerActivity : AppCompatActivity(), View.OnClickListener {

    private var mQuestionList: ArrayList<Question>? = null //ArrayList of questions
    private var mQuestionListIndex: Int = 0 //index in QuestionsList of current question
    private var mSelectedOption: AnswerOptions =
        AnswerOptions.noneSelected//position of answer selected by user
    private var mNumCorrectAnswers: Int = 0 //number of correct answers so far

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_player)

        GamePlayFunctions.getQValEv(10)
        GamePlayFunctions.getQ(10)
        //Initialize fields
        Log.d("tag fuck", "fuckingQuestion: calling getQuestions()")
        mQuestionList = ArrayList()
       // mQuestionList!!.add(0,GamePlayFunctions.getQ(10))
        //mQuestionList!!.add(1,GamePlayFunctions.getQ(12))
        //mQuestionList!!.add(2,GamePlayFunctions.getQ(118))//GamePlayFunctions.getQuestions(10)
        //mQuestionList = GamePlayFunctions.getQuestions(intent.getIntExtra(Constants.NUM_QUESTIONS, 10))

        //Get XML elements
        val tvQuestion = findViewById<TextView>(R.id.tv_question)
        val tvOptA = findViewById<TextView>(R.id.tv_option_a)
        val tvOptB = findViewById<TextView>(R.id.tv_option_b)
        val tvOptC = findViewById<TextView>(R.id.tv_option_c)
        val tvOptD = findViewById<TextView>(R.id.tv_option_d)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        val pbProgress = findViewById<ProgressBar>(R.id.pb_progress)


        tvOptA.setOnClickListener(this)
        tvOptB.setOnClickListener(this)
        tvOptC.setOnClickListener(this)
        tvOptD.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)

        GamePlayFunctions.defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD)
        GamePlayFunctions.setQuestion(
            mQuestionList!![mQuestionListIndex],
            tvOptA,
            tvOptB,
            tvOptC,
            tvOptD,
            tvQuestion
        )
        pbProgress.max = intent.getIntExtra(Constants.NUM_QUESTIONS, 10)
        GamePlayFunctions.updateProgressBar(tvProgress, pbProgress, mQuestionListIndex+1)
    }

    override fun onClick(v: View?) {

        //Get XML Elements
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
                GamePlayFunctions.defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD)
                GamePlayFunctions.selectedOptionView(this, findViewById(R.id.tv_option_a))
                mSelectedOption = AnswerOptions.A
            }
            R.id.tv_option_b -> {
                GamePlayFunctions.defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD)
                GamePlayFunctions.selectedOptionView(this, findViewById(R.id.tv_option_b))
                mSelectedOption = AnswerOptions.B
            }
            R.id.tv_option_c -> {
                GamePlayFunctions.defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD)
                GamePlayFunctions.selectedOptionView(this, findViewById(R.id.tv_option_c))
                mSelectedOption = AnswerOptions.C
            }
            R.id.tv_option_d -> {
                GamePlayFunctions.defaultOptionsView(this, tvOptA, tvOptB, tvOptC, tvOptD)
                GamePlayFunctions.selectedOptionView(this, findViewById(R.id.tv_option_d))
                mSelectedOption = AnswerOptions.D
            }
            R.id.btn_submit -> {
                if (mSelectedOption == AnswerOptions.noneSelected) {
                    ++mQuestionListIndex
                    //if there are no more remaining questions, move to results activity
                    if (mQuestionListIndex == mQuestionList!!.size) {
                        val intent = Intent(this, ResultActivity::class.java)
                        startActivity(intent)
                    }
                    //if there remaining questions, move to next question
                    GamePlayFunctions.defaultOptionsView(this,tvOptA,tvOptB,tvOptC,tvOptD)
                    GamePlayFunctions.setQuestion(
                        mQuestionList!![mQuestionListIndex],
                        tvOptA,
                        tvOptB,
                        tvOptC,
                        tvOptD,
                        tvQuestion
                    )
                    GamePlayFunctions.updateProgressBar(tvProgress, pbProgress, mQuestionListIndex+1)
                    return
                }
                if (mSelectedOption == mQuestionList!![mQuestionListIndex].correctAnswer){
                    mNumCorrectAnswers++
                }else{
                    GamePlayFunctions.answerView(this, tvOptA,tvOptB,tvOptC,tvOptD, mSelectedOption,
                        R.drawable.correct_option_border_bg)
                }
                GamePlayFunctions.answerView(this, tvOptA,tvOptB,tvOptC,tvOptD, mSelectedOption,
                    R.drawable.correct_option_border_bg)
                btnSubmit.text = "Go to next Question"
            }
        }
    }

}
