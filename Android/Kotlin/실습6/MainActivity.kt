package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth
        if (auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        val remoteConfig=Firebase.remoteConfig
        val configSettings= remoteConfigSettings {
            minimumFetchIntervalInSeconds=1
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)


        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) {
                val season = remoteConfig.getString("season")
                if (season == "spring")
                    binding.seasonImage.setImageResource(R.drawable.spring)
                else if (season == "summer")
                    binding.seasonImage.setImageResource(R.drawable.summer)
                else if (season == "fall")
                    binding.seasonImage.setImageResource(R.drawable.fall)
                else if (season == "winter")
                    binding.seasonImage.setImageResource(R.drawable.winter)
            }

        binding.btnRefresh.setOnClickListener {
            remoteConfig.fetchAndActivate().addOnCompleteListener(this) {
                val season = remoteConfig.getString("season")
                if (season == "spring")
                    binding.seasonImage.setImageResource(R.drawable.spring)
                else if (season == "summer")
                    binding.seasonImage.setImageResource(R.drawable.summer)
                else if (season == "fall")
                    binding.seasonImage.setImageResource(R.drawable.fall)
                else if (season == "winter")
                    binding.seasonImage.setImageResource(R.drawable.winter)
            }
        }

    }
}