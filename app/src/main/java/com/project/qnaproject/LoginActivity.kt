package com.project.qnaproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var email:EditText
    lateinit var password: EditText
    lateinit var signin_btn: Button
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.email_in)
        password = findViewById(R.id.password_in)
        val forgot_txt : TextView = findViewById(R.id.textView)
        signin_btn = findViewById(R.id.login_btn)
        val dont_txt : TextView = findViewById(R.id.textView2)
        progressBar = findViewById(R.id.signin_progress)

        dont_txt.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val email_txt = email.text.toString()
        val password_txt = password.text.toString()
        signin_btn.setOnClickListener{
            loginUser()
        }

    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email_reg = email.text.toString()
        val password_reg = password.text.toString()

        if (TextUtils.isEmpty(email_reg) || TextUtils.isEmpty(password_reg)) {
            Toast.makeText(this, "Enter Email or Phone number", Toast.LENGTH_SHORT).show()
            email.error = "Enter email"
            email.requestFocus()
            password.setError("Enter password")
            password.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email_reg, password_reg)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    progressBar.visibility = View.GONE
                    val intent = Intent(baseContext, MainActivity::class.java)
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
