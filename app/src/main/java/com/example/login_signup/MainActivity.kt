package com.example.login_signup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import com.example.login_signup.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var logoutButton: Button

    private val questions = arrayOf("What is GNU's full form?","How many hall's are there in NIT DGP?",
        "When was NIT DGP built?","Which animal is in GLUG's logo?","Which of the following is true " +
                "about OS?")
    private val options= arrayOf(arrayOf("GNU's Not Unix","Google Nano Unit","GLUG NIT UNIT"),
        arrayOf("14","10","18"),
        arrayOf("1960","1969","2007"), arrayOf("Tiger","Penguin","Lion"), arrayOf("Free use","paid"
            ,"Unsafe"))
    private val correctAnswers= arrayOf(0,0,0,1,0)
    private var currentQuestionIndex=0
    private var score=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        logoutButton = findViewById(R.id.logoutButton)
        displayQuestion()
        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }
        binding.restartButton.setOnClickListener {
            restartQuiz()
        }



    }
    private fun correctButtonColors(buttonIndex: Int){
        when(buttonIndex){
            0 ->binding.option1Button.setBackgroundColor(Color.GREEN)
            1 ->binding.option2Button.setBackgroundColor(Color.GREEN)
            2 ->binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }
    private fun wrongButtonColors(buttonIndex: Int){
        when(buttonIndex){
            0 ->binding.option1Button.setBackgroundColor(Color.RED)
            1 ->binding.option2Button.setBackgroundColor(Color.RED)
            2 ->binding.option3Button.setBackgroundColor(Color.RED)

        }
    }
    private  fun resetButtonColor(){
        binding.option1Button.setBackgroundColor(Color.rgb(50,59,96))
        binding.option2Button.setBackgroundColor(Color.rgb(50,59,96))
        binding.option3Button.setBackgroundColor(Color.rgb(50,59,96))
    }
    private fun showResults(){
        Toast.makeText(this,"Your score: $score out of $currentQuestionIndex",Toast.LENGTH_LONG).show()
        binding.restartButton.isEnabled=true
    }
    private fun displayQuestion(){
        binding.questionText.text=questions[currentQuestionIndex]
        binding.option1Button.text=options[currentQuestionIndex][0]
        binding.option2Button.text=options[currentQuestionIndex][1]
        binding.option3Button.text=options[currentQuestionIndex][2]
        resetButtonColor()
    }
    private fun checkAnswer(selectedAnswerIndex: Int){
        val correctAnswerIndex= correctAnswers[currentQuestionIndex]
        if(selectedAnswerIndex==correctAnswerIndex){
            score++
            correctButtonColors(selectedAnswerIndex)
        }else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            binding.questionText.postDelayed({ displayQuestion() }, 1000)
            showResults()
        } else {
            showResults()
            restartQuiz()
        }

    }
    private fun restartQuiz(){
        currentQuestionIndex=0
        score=0
        displayQuestion()
        binding.restartButton.isEnabled=false
    }
}