package com.rsschool.quiz

class Quiz {

    companion object {
        var result = StringBuilder()
        var score = 0
        /*val questions = listOf("Назовите основные принцыпы ООП в Java?",
            "Какие два класса в Java не наследуются от Object?",
            "Как написать immutable класс в Java?",
            "Какой модификатор доступа в Java используется по умолчанию?",
            "Что из перечисленного относится к интерфейсам в Java?")

        val answers = arrayOf(arrayOf("Абстракция", "Полиморфизм", "Наследование",
            "Инкапсуляция", "Все выше перечисленные"),
            arrayOf("Class", "Reflection", "Nothing", "Error",
                "В Java все классы наследуются от Object"),
            arrayOf("сделать класс финальным",
                "сделать все поля приватными и создать только геттеры к ним",
                "сделать все mutable поля final, чтобы установить значение можно было только один раз",
                "инициализировать все поля через конструктор, выполняя глубокое копирование, " +
                        "клонировать объекты mutable переменных в геттерах, чтобы возвращать только " +
                        "копии значений, а не ссылки на актуальные объекты.",
                "все перечисленное"),
            arrayOf("private", "package-private", "protected", "public", "project"),
            arrayOf("ничего из перечисленного",
                "все методы в интерфейсе — protected и abstract",
                "все переменные — private static final",
                "классы наследуют их (extends), а не реализовывают (implements). " +
                        "Причем наследоваться можно только от одного интерфейса",
                "классы, которые реализуют интерфейс, не должны предоставлять реализацию всех методов, " +
                        "которые есть в интерфейсе."))*/

        @JvmStatic
        fun getQuestions(): ArrayList<Question> {
            val questionList = ArrayList<Question>()

            val questionOne = Question(
                1,
                "Назовите основные принцыпы ООП в Java?",
                "Абстракция", "Полиморфизм", "Наследование",
                "Инкапсуляция", "Все выше перечисленные",
                5
            )
            questionList.add(questionOne)

            val questionTwo = Question(
                2,
                "Какие два класса в Java не наследуются от Object?",
                "Class", "В Java все классы наследуются от Object",
                "Reflection", "Nothing", "Error",
                2
            )
            questionList.add(questionTwo)

            val questionThree = Question(
                3,
                "Как написать immutable класс в Java?",
                "все перечисленное",
                "сделать класс финальным",
                "сделать все поля приватными и создать только геттеры к ним",
                "сделать все mutable поля final, чтобы установить значение можно было только один раз",
                "инициализировать все поля через конструктор, выполняя глубокое копирование, " +
                        "клонировать объекты mutable переменных в геттерах, чтобы возвращать только " +
                        "копии значений, а не ссылки на актуальные объекты",
                1
            )
            questionList.add(questionThree)

            val questionFour = Question(
                4,
                "Какой модификатор доступа в Java используется по умолчанию?",
                "private", "package-private", "protected",
                "public", "project",
                2
            )
            questionList.add(questionFour)

            val questionFive = Question(
                5,
                "Что из перечисленного относится к интерфейсам в Java?",
                "все методы в интерфейсе — protected и abstract",
                "все переменные — private static final",
                "ничего из перечисленного",
                "классы наследуют их (extends), а не реализовывают (implements), " +
                        "наследоваться можно только от одного интерфейса",
                "классы, которые реализуют интерфейс, не должны предоставлять реализацию всех методов, " +
                        "которые есть в интерфейсе",
                3
            )
            questionList.add(questionFive)

            return questionList
        }
    }
}