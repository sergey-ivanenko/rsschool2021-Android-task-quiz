package com.rsschool.quiz

class Question(
    val id: Int,
    val question: String,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val optionFive: String,
    val correctAnswer: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (id != other.id) return false
        if (question != other.question) return false
        if (optionOne != other.optionOne) return false
        if (optionTwo != other.optionTwo) return false
        if (optionThree != other.optionThree) return false
        if (optionFour != other.optionFour) return false
        if (optionFive != other.optionFive) return false
        if (correctAnswer != other.correctAnswer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + question.hashCode()
        result = 31 * result + optionOne.hashCode()
        result = 31 * result + optionTwo.hashCode()
        result = 31 * result + optionThree.hashCode()
        result = 31 * result + optionFour.hashCode()
        result = 31 * result + optionFive.hashCode()
        result = 31 * result + correctAnswer
        return result
    }
}