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

class ResetPassword : AppCompatActivity() {

    lateinit var reset_btn : Button
    lateinit var auth : FirebaseAuth
    lateinit var email: EditText
    lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        auth = FirebaseAuth.getInstance()

        reset_btn = findViewById(R.id.reset_btn)
        email = findViewById(R.id.reset_email)
        progress = findViewById(R.id.progressBar)

        reset_btn.setOnClickListener {
           reset()

        }
    }

    private fun reset(){
        val email_txt = email.text.toString()
        if (TextUtils.isEmpty(email_txt)){
            email.error = "Enter email address"
            email.requestFocus()
            return
        }
        progress.visibility = View.VISIBLE
        auth.sendPasswordResetEmail(email_txt)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progress.visibility = View.GONE
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

    }
}
