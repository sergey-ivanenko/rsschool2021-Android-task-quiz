package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), QuizFragment.Navigator {

    private val questionList:ArrayList<Question> = Quiz.getQuestions()
    private var currentPosition = 1
    private var selectedOptionPosition = 0
    private val fragments: ArrayList<Fragment> = ArrayList()
    private var currentNumberOfQuestion = 1
    private val scoreList = MutableList(questionList.size) { _ -> 0 }

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            Toast.makeText(this, "${fragments.size}", Toast.LENGTH_LONG).show()
        }
    }

    /*fun getScreenCount(): Int {
        return supportFragmentManager.backStackEntryCount + 1
    }*/

    //fun getQuestion(numberOfQuestion: Int): Question = questionList[numberOfQuestion - 1]

    //fun getFragmentsContainerSize() = fragments?.size ?: 0

    override fun goBack() {
        if(currentNumberOfQuestion != 1) {
            currentNumberOfQuestion -= 1
            //super.onBackPressed()
            /*Toast.makeText(this, "BACK CLICKED!!!", Toast.LENGTH_SHORT).show()*/
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragments[currentNumberOfQuestion - 1])
                //.addToBackStack(null)
                .commit()
        } else {
            super.onBackPressed()
        }
    }

    override fun launchNext() {
        if (currentNumberOfQuestion == questionList.size) return
        currentNumberOfQuestion += 1

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
                //.addToBackStack(null)
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

        Toast.makeText(this, "${fragments.size}", Toast.LENGTH_LONG).show()
    }

    override fun chooseAnswer(numberOfAnswer: Int) {
        val index = currentNumberOfQuestion - 1
        val question = questionList[index]
        var score: Int = 0
        if(question.correctAnswer == numberOfAnswer) {
            score = ((1.0 / questionList.size) * 100).toInt()
            //Quiz.score += ((1.0 / questionList.size) * 100).toInt()

        }
        scoreList[index] = score

        Quiz.score = scoreList.sum()
        //Toast.makeText(this, "Answer is ${Quiz.score}", Toast.LENGTH_SHORT).show()
    }

    override fun submitQuiz() {
        Toast.makeText(this, "SUB = ${Quiz.result}", Toast.LENGTH_SHORT).show()

    }

    override fun onBackPressed() {
        goBack()
    }
}