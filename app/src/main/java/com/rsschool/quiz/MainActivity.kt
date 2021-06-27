package com.rsschool.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), QuizFragment.Navigator, ResultFragment.ResultEventListener {

    private val questionList:ArrayList<Question> = Quiz.getQuestions()
    private val fragments: ArrayList<Fragment> = ArrayList()
    private var currentNumberOfQuestion = 1
    private val scoreList = MutableList(questionList.size) { _ -> 0 }
    private val resultList = MutableList(questionList.size) { _ -> "" }
    private var currentTheme = DEEP_ORANGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeThemeColor()

        if (savedInstanceState == null) {
            generateQuizFragment()
        }
    }

    private fun generateQuizFragment() {
        val question: Question = questionList[currentNumberOfQuestion - 1]

        val quizFragment = QuizFragment.newInstance(
            id = question.id,
            question = question.question,
            optionOne = question.optionOne,
            optionTwo = question.optionTwo,
            optionThree = question.optionThree,
            optionFour = question.optionFour,
            optionFive = question.optionFive,
            correctAnswer = question.correctAnswer
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, quizFragment)
            .commit()

        fragments.add(quizFragment)
    }

    private fun changeThemeColor() {
        val typedValue = TypedValue()
        currentTheme = when(currentNumberOfQuestion) {
            1 -> DEEP_ORANGE
            2 -> YELLOW
            3 -> CYAN
            4 -> DEEP_PURPLE
            5 -> LIGHT_GREEN
            else -> DEEP_ORANGE
        }
        setTheme(currentTheme)
        theme.resolveAttribute(android.R.attr.statusBarColor, typedValue, true)
        val color = typedValue.data
        window.statusBarColor = color
    }

    override fun goBack() {
        if(currentNumberOfQuestion != 1) {
            currentNumberOfQuestion -= 1

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragments[currentNumberOfQuestion - 1])
                .commit()

            changeThemeColor()
        } else {
            super.onBackPressed()
        }
    }

    override fun launchNext() {
        currentNumberOfQuestion += 1
        changeThemeColor()

        val numberOfQuestions = fragments.size

        // if it's a new question - generate fragment, else use fragment from list
        if (numberOfQuestions < currentNumberOfQuestion) {
            generateQuizFragment()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragments[currentNumberOfQuestion - 1])
                .commit()
        }
    }

    override fun chooseAnswer(numberOfAnswer: Int, answer: String) {
        val index = currentNumberOfQuestion - 1
        val question = questionList[index]
        var currentScore = 0

        if(question.correctAnswer == numberOfAnswer) {
            currentScore = ((1.0 / questionList.size) * 100).toInt()
        }
        scoreList[index] = currentScore
        resultList[index] = answer

        Quiz.score = scoreList.sum()
    }

    override fun submitQuiz() {
        val resultFragment = ResultFragment.newInstance(
            score = Quiz.score,
            answers = Quiz.result.toString()
        )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, resultFragment)
            .commit()
    }

    override fun onBackPressed() {
        goBack()
    }

    override fun onShareImageClickListener() {
        // generate final report
        val shareResult = StringBuilder(Quiz.result)
        resultList.forEach { shareResult.append(it) }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareResult.toString().format(Quiz.score))
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    override fun onBackImageClickListener() {
        // set values by default and generate first fragment
        fragments.clear()
        Quiz.score = 0
        currentNumberOfQuestion = 1
        changeThemeColor()
        generateQuizFragment()
    }

    override fun onCloseImageClickListener() {
        this.finishAndRemoveTask()
    }

    companion object {
        private const val DEEP_ORANGE = R.style.Theme_Quiz_First
        private const val YELLOW = R.style.Theme_Quiz_Second
        private const val CYAN = R.style.Theme_Quiz_Third
        private const val DEEP_PURPLE = R.style.Theme_Quiz_Fours
        private const val LIGHT_GREEN = R.style.Theme_Quiz_Fives
    }
}