package com.example.login_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login_signup.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            Toast.makeText(this,"Log in successful",Toast.LENGTH_SHORT).show()
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Log in Unsuccessful",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this,"Enter a valid email and password",Toast.LENGTH_SHORT).show()
            }
        }
        binding.signupText.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
            finish()
        }
    }

}