package com.example.firebaseauth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseauth.databinding.ItemBinding
import kotlinx.android.synthetic.main.item.view.*

data class Items(val id:String, val cart: Boolean, val name: String, val price: Number)

class MyViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(private val context: Context, private var items: List<Items>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemBinding = ItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textId.text = item.id
        holder.binding.textName.text = item.name
        val cart=item.cart
        if(cart==true)
            holder.binding.textCart.text="In Cart"
        else
            holder.binding.textCart.text=null

        holder.itemView.setOnClickListener {
            Log.d("tag", item.price.toString())
            val intent = Intent(holder.itemView?.context, ItemActivity::class.java)
            intent.putExtra("id",item.id)
            intent.putExtra("name",item.name)
            intent.putExtra("price",item.price.toString())
            intent.putExtra("cart",item.cart)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount() = items.size

    fun updateList(newList: List<Items>) {
        items = newList
        notifyDataSetChanged()
    }
}
