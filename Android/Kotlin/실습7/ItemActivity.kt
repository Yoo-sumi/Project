package com.example.firebaseauth

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauth.databinding.ActivityItemBinding
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemBinding
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textName.text=intent.getStringExtra("name")
        binding.textPrice.text=intent.getStringExtra("price")
        val cart=intent.getBooleanExtra("cart",false)
        if(cart==false)
            binding.btnCart.text = "ADD TO CART"
        else
            binding.btnCart.text = "REMOVE FROM CART"
        binding.btnCart.setOnClickListener {
            val itemID=intent.getStringExtra("id")
            itemsCollectionRef.document(itemID!!).update("cart",!cart)
                .addOnSuccessListener {
                    if(cart==false)  binding.btnCart.text = "REMOVE FROM CART"
                    else binding.btnCart.text = "ADD TO CART"
                }
        }
    }
}