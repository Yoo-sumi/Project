package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val button=findViewById<Button>(R.id.button)
        button.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(applicationContext,"Test",Toast.LENGTH_SHORT).show()
            }
        })*/

        val button=findViewById<Button>(R.id.button)
        button.setOnClickListener{
                Toast.makeText(applicationContext,"Test",Toast.LENGTH_SHORT).show()
        }
    }
}