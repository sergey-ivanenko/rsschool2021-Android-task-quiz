package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    interface Navigator {
        fun goBack()
        fun launchNext()
        fun chooseAnswer(numberOfAnswer: Int)
        fun submitQuiz()
    }

    private var navigator: Navigator? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding: FragmentQuizBinding get() = requireNotNull(_binding)



    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator?
        /*if (activity !is QuizEventListener) {
            throw IllegalArgumentException("Activity should implement QuizEventListener interface!")
        }*/
        //quizEventListener = activity as QuizEventListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_quiz, container, false)
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        binding.previousButton.isEnabled = false
        binding.nextButton.isEnabled = false
        binding.toolbar.title = getString(R.string.question_number, getNumberOfQuestion())
        binding.question.text = requireArguments().getString(ARG_QUESTION)
        binding.optionOne.text = requireArguments().getString(ARG_OPTION_ONE)
        binding.optionTwo.text = requireArguments().getString(ARG_OPTION_TWO)
        binding.optionThree.text = requireArguments().getString(ARG_OPTION_THREE)
        binding.optionFour.text = requireArguments().getString(ARG_OPTION_FOUR)
        binding.optionFive.text = requireArguments().getString(ARG_OPTION_FIVE)
        if (getNumberOfQuestion() == Quiz.getQuestions().size) {
            binding.nextButton.text = "Submit"
        }

        binding.nextButton.setOnClickListener { onNextButtonClicked() }
        binding.previousButton.setOnClickListener { onPrevButtonClicked() }
        binding.toolbar.setNavigationOnClickListener { onPrevButtonClicked() }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val button = group.findViewById<RadioButton>(checkedId)
            /*Toast.makeText(context,
                "CLICKED! id = ${group.indexOfChild(button)}",
                Toast.LENGTH_SHORT).show()*/
            navigator?.chooseAnswer(group.indexOfChild(button) + 1)
            when (getNumberOfQuestion()) {
                1 -> {
                    binding.previousButton.isEnabled = false
                    binding.nextButton.isEnabled = true
                }
                else -> {
                    binding.previousButton.isEnabled = true
                    binding.nextButton.isEnabled = true
                }
            }
            Quiz.result
                .append(requireArguments().getString(ARG_QUESTION))
                .append("\n")
                .append("Your answer: ")
                .append(button.text)
                .append("\n")
                .append("\n")
        }

        val view = binding.root
        return view
    }

    private fun getNumberOfQuestion(): Int = requireArguments().getInt(ARG_ID)

    private fun onPrevButtonClicked() {
        navigator?.goBack()

        Log.i("click", "CLICK")
        /*if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.fragments[getNumberOfQuestion()]
        } else {
            requireActivity().onBackPressed()
        }*/

        //val size = parentFragmentManager.fragments.size
        //Toast.makeText(context, "SIZE = $size", Toast.LENGTH_SHORT).show()
        /*requireActivity().onBackPressed()*/
        /*if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
        } else {
            requireActivity().onBackPressed()
        }*/
    }

    private fun onNextButtonClicked() {
        when (binding.nextButton.text.toString()) {
            "Next" -> navigator?.launchNext()
            "Submit" -> navigator?.submitQuiz()
        }


        /*parentFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()*/
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(id: Int,
                        question: String,
                        optionOne: String,
                        optionTwo: String,
                        optionThree: String,
                        optionFour: String,
                        optionFive: String,
                        correctAnswer: Int): QuizFragment {

            val args = Bundle().apply {
                putInt(ARG_ID, id)
                putString(ARG_QUESTION, question)
                putString(ARG_OPTION_ONE, optionOne)
                putString(ARG_OPTION_TWO, optionTwo)
                putString(ARG_OPTION_THREE, optionThree)
                putString(ARG_OPTION_FOUR, optionFour)
                putString(ARG_OPTION_FIVE, optionFive)
                putInt(ARG_CORRECT_ANSWER, correctAnswer)
            }
            val fragment = QuizFragment()
            fragment.arguments = args

            /*val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args*/
            return fragment
        }

        private const val ARG_ID = "ARG_ID"
        private const val ARG_QUESTION = "ARG_QUESTION"
        private const val ARG_OPTION_ONE = "ARG_OPTION_ONE"
        private const val ARG_OPTION_TWO = "ARG_OPTION_TWO"
        private const val ARG_OPTION_THREE = "ARG_OPTION_THREE"
        private const val ARG_OPTION_FOUR = "ARG_OPTION_FOUR"
        private const val ARG_OPTION_FIVE = "ARG_OPTION_FIVE"
        private const val ARG_CORRECT_ANSWER = "ARG_CORRECT_ANSWER"
    }

}