package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding get() = requireNotNull(_binding)

    private var resultEvents: ResultEventListener? = null

    interface ResultEventListener {
        fun onShareImageClickListener()
        fun onBackImageClickListener()
        fun onCloseImageClickListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultEvents = context as ResultEventListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.result.text = getString(R.string.result_text, requireArguments().getInt(ARG_SCORE))
        binding.imageShare.setOnClickListener { sharedResult() }
        binding.imageBack.setOnClickListener { restartQuiz() }
        binding.imageClose.setOnClickListener { closeQuiz() }

        return binding.root
    }

    private fun sharedResult() {
        resultEvents?.onShareImageClickListener()
    }

    private fun restartQuiz() {
        resultEvents?.onBackImageClickListener()
    }

    private fun closeQuiz() {
        resultEvents?.onCloseImageClickListener()
    }

    override fun onDetach() {
        super.onDetach()
        resultEvents = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(score: Int, answers: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SCORE, score)
                    putString(ARG_ANSWERS, answers)
                }
            }

        private const val ARG_SCORE = "ARG_SCORE"
        private const val ARG_ANSWERS = "ARG_ANSWERS"
    }
}