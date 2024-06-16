package hr.algebra.coctailsapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import hr.algebra.coctailsapp.R

class QuizFragment : Fragment() {

    private lateinit var questionTextView: TextView
    private lateinit var answersRadioGroup: RadioGroup
    private lateinit var submitAnswerButton: Button
    private lateinit var retryButton: Button

    private val questions = listOf(
        Question("What is the main ingredient in a Margarita?", listOf("Vodka", "Tequila", "Rum", "Gin"), "Tequila"),
        Question("Which spirit is made from sugarcane?", listOf("Whiskey", "Vodka", "Rum", "Gin"), "Rum"),
        Question("Which cocktail contains gin, vermouth, and Campari?", listOf("Martini", "Negroni", "Margarita", "Old Fashioned"), "Negroni"),
        Question("What is the main ingredient in a Bloody Mary?", listOf("Vodka", "Tequila", "Whiskey", "Rum"), "Vodka"),
        Question("Which spirit is typically used in a Mojito?", listOf("Tequila", "Rum", "Vodka", "Gin"), "Rum")
    )

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        questionTextView = view.findViewById(R.id.questionTextView)
        answersRadioGroup = view.findViewById(R.id.answersRadioGroup)
        submitAnswerButton = view.findViewById(R.id.submitAnswerButton)
        retryButton = view.findViewById(R.id.retryButton)

        submitAnswerButton.setOnClickListener {
            val selectedAnswerId = answersRadioGroup.checkedRadioButtonId
            if (selectedAnswerId != -1) {
                val selectedAnswer = view.findViewById<RadioButton>(selectedAnswerId)
                checkAnswer(selectedAnswer.text.toString())
            } else {
                Toast.makeText(context, "Please select an answer", Toast.LENGTH_SHORT).show()
            }
        }

        retryButton.setOnClickListener {
            resetQuiz()
        }

        loadQuestion()

        return view
    }

    private fun loadQuestion() {
        val question = questions[currentQuestionIndex]
        questionTextView.text = question.text
        answersRadioGroup.removeAllViews()
        question.answers.forEach { answer ->
            val radioButton = RadioButton(context)
            radioButton.text = answer
            answersRadioGroup.addView(radioButton)
        }
    }

    private fun checkAnswer(answer: String) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer
        if (answer == correctAnswer) {
            score++
            Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Incorrect! The correct answer is $correctAnswer.", Toast.LENGTH_SHORT).show()
        }

        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            loadQuestion()
        } else {
            showResult()
        }
    }

    private fun showResult() {
        questionTextView.text = "You scored $score out of ${questions.size}."
        answersRadioGroup.visibility = View.GONE
        submitAnswerButton.visibility = View.GONE
        retryButton.visibility = View.VISIBLE
    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
        answersRadioGroup.visibility = View.VISIBLE
        submitAnswerButton.visibility = View.VISIBLE
        retryButton.visibility = View.GONE
        loadQuestion()
    }
}

data class Question(val text: String, val answers: List<String>, val correctAnswer: String)
