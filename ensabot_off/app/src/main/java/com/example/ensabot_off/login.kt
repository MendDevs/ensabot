package com.example.ensabot_off

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

@Suppress("DEPRECATION")
class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val logInButton: Button = findViewById(R.id.startBtn)
        val name: EditText = findViewById(R.id.SessionName)

        logInButton.setOnClickListener {
            val input = name.text.toString()
            val activity2 = Intent(this, chat::class.java)
            val vibrateur = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (input.isNotEmpty()) {
                // Sending nickname to activity 2
                activity2.putExtra("SessionNameExtra", input)

                // Starting activity 2
                startActivity(activity2)
            } else if (vibrateur.hasVibrator()) {
                vibrateur.vibrate(500)
                Toast.makeText(this, "Enter name to start session", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
