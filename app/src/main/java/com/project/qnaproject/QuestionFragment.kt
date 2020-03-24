package com.project.qnaproject


import Answer
import Question
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.project.qnaproject.services.QuestionService
import qa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment() {
    lateinit var questions: List<Question>
    private var questionIndex = 0
    lateinit var question: TextView
    lateinit var firstAnswer: RadioButton
    lateinit var secondAnswer: RadioButton
    lateinit var thirdAnswer: RadioButton
    lateinit var fourthAnswer: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val characterService = QuestionService.Factory.create()
        val qas: Call<qa> = characterService.getQuestionsAndAnswers()
        qas.enqueue(object : Callback<qa> {
            override fun onFailure(call: Call<qa>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<qa>,
                response: Response<qa>
            ) {

                questions = response.body()!!.questions
            }


        })
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        question = view.findViewById(R.id.question)
        firstAnswer = view.findViewById(R.id.firstAnswer)
        secondAnswer = view.findViewById(R.id.secondAnswer)
        thirdAnswer = view.findViewById(R.id.thirdAnswer)
        fourthAnswer = view.findViewById(R.id.fourthAnswer)

        question.text = questions[questionIndex].question
        firstAnswer.text = questions[questionIndex].answers[0].option
        secondAnswer.text = questions[questionIndex].answers[1].option
        thirdAnswer.text = questions[questionIndex].answers[2].option
        fourthAnswer.text = questions[questionIndex].answers[3].option

    }
}
