package com.example.ensachatbot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val logInButton: Button = findViewById(R.id.startBtn)
        val name: EditText = findViewById(R.id.SessionName)

        logInButton.setOnClickListener {
            val input = name.text.toString().trim()
            val activity2 = Intent(this, chat::class.java)
            val vibrateur = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

            if (input.isNotEmpty()) {
                activity2.putExtra("username", input)
                try {
                    startActivity(activity2)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error starting chat activity: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (vibrateur?.hasVibrator() == true) {
                    vibrateur.vibrate(500)
                    Toast.makeText(this, "Enter name to start session", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter your name to start the session", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
