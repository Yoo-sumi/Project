package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebaseauth.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.login.setOnClickListener {
            val userEmail = binding.username.text.toString()
            val password = binding.password.text.toString()
            auth.signInWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                        finish()
                    } else {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        }

        binding.signup.setOnClickListener {
            val userEmail = binding.username.text.toString()
            val password = binding.password.text.toString()
            auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )
                        finish()
                    } else {
                        Toast.makeText(this, "failed.", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}