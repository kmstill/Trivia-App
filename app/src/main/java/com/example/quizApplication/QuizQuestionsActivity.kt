package com.example.quizApplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1 //index in QuestionsList of current question
    private var mQuestionsList:ArrayList<Question>?= null //ArrayList of questions
    private var mSelectedOptionPosition:Int = 0 //position of answer selected by user
    private var mCorrectAnswers:Int = 0 //number of correct answers so far
    private var mUserName: String? = null

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList=Constants.getQuestions()

        setQuestion()

        findViewById<TextView>(R.id.tv_option_one).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_option_two).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_option_three).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_option_four).setOnClickListener(this)
        findViewById<Button>(R.id.btn_submit).setOnClickListener(this)
    }

    /**
     *
     */
    private fun setQuestion(){
        val question = mQuestionsList!![mCurrentPosition-1]

        defaultOptionsView()

        if(mCurrentPosition==mQuestionsList!!.size){
            findViewById<Button>(R.id.btn_submit).text="FINISH"
        }else{
            findViewById<Button>(R.id.btn_submit).text="SUBMIT"
        }

        findViewById<ProgressBar>(R.id.progress_bar).progress = mCurrentPosition
        findViewById<TextView>(R.id.tv_progress).text = "$mCurrentPosition" + "/" + findViewById<ProgressBar>(R.id.progress_bar).max

        findViewById<TextView>(R.id.tv_question).text=question!!.question
        findViewById<ImageView>(R.id.iv_image).setImageResource(question.image)
        findViewById<TextView>(R.id.tv_option_one).text=question.optionOne
        findViewById<TextView>(R.id.tv_option_two).text=question.optionTwo
        findViewById<TextView>(R.id.tv_option_three).text=question.optionThree
        findViewById<TextView>(R.id.tv_option_four).text=question.optionFour
    }

    /**
     *
     */
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,findViewById<TextView>(R.id.tv_option_one))
        options.add(1,findViewById<TextView>(R.id.tv_option_two))
        options.add(2,findViewById<TextView>(R.id.tv_option_three))
        options.add(3,findViewById<TextView>(R.id.tv_option_four))

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background= ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }
    }

    /**
     *
     */
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one ->{
                selectedOptionView(findViewById<TextView>(R.id.tv_option_one), selectedOptionNum=1)
            }
            R.id.tv_option_two ->{
                selectedOptionView(findViewById<TextView>(R.id.tv_option_two), selectedOptionNum=2)
            }
            R.id.tv_option_three ->{
                selectedOptionView(findViewById<TextView>(R.id.tv_option_three), selectedOptionNum=3)
            }
            R.id.tv_option_four ->{
                selectedOptionView(findViewById<TextView>(R.id.tv_option_four), selectedOptionNum=4)
            }
            R.id.btn_submit ->{
                if(mSelectedOptionPosition==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionsList!!.size ->{
                            setQuestion()
                        }else ->{
                        val intent= Intent(this,ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                        startActivity(intent)

                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border_bg) //if incorrect answer is selected, make it red
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg) //always make correct answer green
                    if(mCurrentPosition == mQuestionsList!!.size){
                        findViewById<Button>(R.id.btn_submit).text="FINISH"
                    }else{
                        findViewById<Button>(R.id.btn_submit).text="GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
            1 ->{
                findViewById<TextView>(R.id.tv_option_one).background=ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            2 ->{
                findViewById<TextView>(R.id.tv_option_two).background=ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            3 ->{
                findViewById<TextView>(R.id.tv_option_three).background=ContextCompat.getDrawable(
                    this, drawableView
                )
            }
            4 ->{
                findViewById<TextView>(R.id.tv_option_four).background=ContextCompat.getDrawable(
                    this, drawableView
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background= ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg)
    }
}