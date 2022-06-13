package com.example.firebaseauth

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseauth.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
        binding.textView.text="FCM Token: (copied to clipboard)"


        FirebaseMessaging.getInstance().token.addOnCompleteListener { // it: Task<String!>
            binding.textFCMToken.text = if (it.isSuccessful) it.result else "Token Error!"
// copy FCM token to clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("FCM Token", binding.textFCMToken.text)
            clipboard.setPrimaryClip(clip)
// write to logcat
            Log.d(MyFirebaseMessagingService.TAG, "FCM token: ${binding.textFCMToken.text}")
        }

        Log.d("MyFirebaseMessaging","!!!!")
        binding.msgBody.text=intent.getStringExtra("message")

    }


}