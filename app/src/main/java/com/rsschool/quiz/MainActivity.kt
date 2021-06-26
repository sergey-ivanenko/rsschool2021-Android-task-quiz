package com.rsschool.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), QuizFragment.Navigator, ResultFragment.ResultEventListener {

    private val questionList:ArrayList<Question> = Quiz.getQuestions()
    private var currentPosition = 1
    private var selectedOptionPosition = 0
    private val fragments: ArrayList<Fragment> = ArrayList()
    private var currentNumberOfQuestion = 1
    private val scoreList = MutableList(questionList.size) { _ -> 0 }
    private val resultList = MutableList(questionList.size) { _ -> "" }
    private var currentTheme = DEEP_ORANGE

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setTheme(R.style.Theme_Quiz_Second)
        //this.window.statusBarColor = Color.GREEN
        //val theme: Resources.Theme = this.theme
        changeThemeColor(/*Color.GREEN*/)

        val question: Question = questionList[currentPosition - 1]

        if (savedInstanceState == null) {
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
    }

    private fun changeThemeColor(/*resourceColor: Int*/) {
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
        //window.statusBarColor = ContextCompat.getColor(applicationContext, resourceColor)
        //window.statusBarColor = resources.getColor(resourceColor, theme)

        /*val bar: ActionBar? = supportActionBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bar?.setBackgroundDrawable(ColorDrawable(resources.getColor(resourseColor, this.theme)))
        }*/


    }

    /*fun getScreenCount(): Int {
        return supportFragmentManager.backStackEntryCount + 1
    }*/

    //fun getQuestion(numberOfQuestion: Int): Question = questionList[numberOfQuestion - 1]

    //fun getFragmentsContainerSize() = fragments?.size ?: 0

    override fun goBack() {
        if(currentNumberOfQuestion != 1) {
            currentNumberOfQuestion -= 1
            changeThemeColor()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragments[currentNumberOfQuestion - 1])
                .commit()
        } else {
            super.onBackPressed()
        }
    }

    override fun launchNext() {
        if (currentNumberOfQuestion == questionList.size) return
        currentNumberOfQuestion += 1
        changeThemeColor()

        val numberOfQuestions = fragments.size
        val question = questionList[currentNumberOfQuestion - 1]

        if (numberOfQuestions < currentNumberOfQuestion) {
            val fragment = QuizFragment.newInstance(
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
                .replace(R.id.container, fragment)
                .commit()
            fragments.add(fragment)
            //currentFragment?.let { fragments.add(it) }
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragments[currentNumberOfQuestion - 1])
                .commit()
        }
        //currentNumberOfQuestion += 1
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
        resultList.forEach { Quiz.result.append(it) }
        val shareResult = Quiz.result.toString().format(Quiz.score)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            //putExtra(Intent.EXTRA_TEXT, Quiz.result.toString().format(Quiz.score))
            putExtra(Intent.EXTRA_TEXT, shareResult/*Quiz.result.toString()*/)
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    override fun onBackImageClickListener() {
        fragments.clear()
        Quiz.result.clear()
        Quiz.score = 0
        currentNumberOfQuestion = 1
        val question = questionList[currentNumberOfQuestion - 1]
        changeThemeColor()
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
        currentPosition += 1
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