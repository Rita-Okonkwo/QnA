package com.project.qnaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var signUp_btn: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.email_address_up)
        password = findViewById(R.id.password_up)
        signUp_btn = findViewById(R.id.sign_up_btn)
        progressBar = findViewById(R.id.signup_progress)

        signUp_btn.setOnClickListener {
            createUser()
        }


    }

    fun createUser(){
        val email_reg = email.text.toString()
        val password_reg = password.getText().toString()

        if (TextUtils.isEmpty(email_reg) || TextUtils.isEmpty(password_reg)) {
            Toast.makeText(this, "Enter Email or Password", Toast.LENGTH_SHORT).show()
            email.setError("Enter email")
            email.requestFocus()
            password.setError("Enter password")
            password.requestFocus()
            return
        }

        progressBar.setVisibility(View.VISIBLE)
        mAuth.createUserWithEmailAndPassword(email_reg, password_reg)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Succesfully registered", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

                // ...
            }
    }
}
