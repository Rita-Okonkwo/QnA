package com.project.qnaproject


import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.project.qnaproject.services.QuestionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class QuestionFragment : Fragment() {
     var questionss: List<com.project.qnaproject.Question> = ArrayList<com.project.qnaproject.Question>()
    private var questionIndex = 0
    private var pointCount = 0
    lateinit var point: TextView
    lateinit var question: TextView
    lateinit var firstAnswer: RadioButton
    lateinit var secondAnswer: RadioButton
    lateinit var thirdAnswer: RadioButton
    lateinit var fourthAnswer: RadioButton
    lateinit var body: qa
    lateinit var submit_btn: Button
    lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if(savedInstanceState != null){
            pointCount = savedInstanceState.getInt("point")
            questionss = savedInstanceState.getParcelableArrayList<com.project.qnaproject.Question>("questions")!!.toList()
        }

        question = view.findViewById(R.id.question)
        firstAnswer = view.findViewById(R.id.firstAnswer)
        secondAnswer = view.findViewById(R.id.secondAnswer)
        thirdAnswer = view.findViewById(R.id.thirdAnswer)
        fourthAnswer = view.findViewById(R.id.fourthAnswer)
        submit_btn = view.findViewById(R.id.submit_btn)
        radioGroup = view.findViewById(R.id.question_group)
        point = view.findViewById(R.id.points_value)

        val characterService = QuestionService.Factory.create()
        val qas: Call<com.project.qnaproject.qa> = characterService.getQuestionsAndAnswers()
        qas.enqueue(object : Callback<com.project.qnaproject.qa> {
            override fun onFailure(call: Call<com.project.qnaproject.qa>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<com.project.qnaproject.qa>,
                response: Response<com.project.qnaproject.qa>
            ) {
                if (savedInstanceState == null) {
                    questionss = response.body()!!.questions
                }

                radioGroup.clearCheck()

                setQuestions()

                submit_btn.setOnClickListener{
                    val checkedId = radioGroup.checkedRadioButtonId
                    var answerIndex = -1
                    if(checkedId != -1){
                        when(checkedId){
                            R.id.firstAnswer -> answerIndex = 0
                            R.id.secondAnswer -> answerIndex = 1
                            R.id.thirdAnswer -> answerIndex = 2
                            R.id.fourthAnswer -> answerIndex = 3
                        }

                        if(questionss[questionIndex].answers[answerIndex].value == true){
                            pointCount = pointCount + 1
                            if (questionIndex < questionss.size - 1){
                                questionIndex++
                                radioGroup.clearCheck()
                                setQuestions()
                            }else{
                                view.findNavController().navigate(QuestionFragmentDirections.actionQuestionFragment2ToGameWonFragment(pointCount, questionss.size - 1))
                            }


                        }else{
                            pointCount = pointCount - 1
                            if(pointCount < 0){
                                view.findNavController().navigate(R.id.action_questionFragment2_to_gameOverFragment)
                            }else{
                                if (questionIndex < questionss.size - 1){
                                    questionIndex++
                                    radioGroup.clearCheck()
                                    setQuestions()
                                }
                            }
                        }
                    }


                }
            }


        })



        }
    private fun setQuestions(){
        question.text = questionss[questionIndex].question
        firstAnswer.text = questionss[questionIndex].answers[0].option
        secondAnswer.text = questionss[questionIndex].answers[1].option
        thirdAnswer.text = questionss[questionIndex].answers[2].option
        fourthAnswer.text = questionss[questionIndex].answers[3].option
        point.text = pointCount.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("point", pointCount)
        outState.putParcelableArrayList("questions", ArrayList<Parcelable>(questionss))
    }
}
