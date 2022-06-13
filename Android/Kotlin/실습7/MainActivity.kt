package com.example.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseauth.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    private var adapter: MyAdapter? =  MyAdapter(this, emptyList())
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    private var snapshotListener: ListenerRegistration? = null

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




    }
    override fun onStart() {
        super.onStart()
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter

        val items = mutableListOf<Items>()
        // snapshot listener for all items
       snapshotListener = itemsCollectionRef.addSnapshotListener { snapshot, error ->
           for (doc in snapshot!!.documentChanges) {
               var cart=doc.document.data["cart"]
               if(cart ==null)
                   cart=false
               items.add(Items(
                   doc.document.id,cart as Boolean, doc.document.data["name"] as String,
                   doc.document.data["price"] as Number
               ))
           }
           adapter?.updateList(items)
        }
        // sanpshot listener for single item

        /*itemsCollectionRef.document("AOfblFod6AKh4GuvuOjY").addSnapshotListener { snapshot, error ->
            Log.d("TAG", "${snapshot?.id} ${snapshot?.data}")
        }*/
    }
    override fun onStop() {
        super.onStop()
        snapshotListener?.remove()
    }
}