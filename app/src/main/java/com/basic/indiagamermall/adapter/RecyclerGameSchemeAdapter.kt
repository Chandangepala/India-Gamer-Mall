package com.basic.indiagamermall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
import com.basic.indiagamermall.activities.GameSchemeActivity
import com.basic.indiagamermall.models.ProductModel

class RecyclerGameSchemeAdapter(private val arrGameSchemeList: ArrayList<ProductModel>, val context: Context) :
    RecyclerView.Adapter<RecyclerGameSchemeAdapter.GameSchemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGameSchemeAdapter.GameSchemeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_scheme_item, parent,
            false);
        return RecyclerGameSchemeAdapter.GameSchemeViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 10;
    }

    override fun onBindViewHolder(holder: RecyclerGameSchemeAdapter.GameSchemeViewHolder, position: Int) {
        holder.txtTitle.setText("Need Id and password")
        holder.txtDiamond.setText("1090" + " Diamond")
        holder.txtPrice.setText("Rs. " + "500")
        holder.txtBuy.setOnClickListener{
            val tagLine = "Hiiii, I Want to buy this Game scheme: "
            val title = "Title: " + holder.txtTitle.text
            val diamond = "Diamond: " + holder.txtDiamond.text
            val price = "Price: " + holder.txtPrice.text

            val message = "$tagLine\r \n$title\r \n$diamond\r \n$price"
            val phoneNumber = "917357678251"
            // Calling the function
            if (context is GameSchemeActivity) {
                (context as GameSchemeActivity). sendMessageViaWhatsapp(message,phoneNumber)
            }

        }
    }

    class GameSchemeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtBuy = itemView.findViewById<TextView>(R.id.txtBuy)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtDiamond = itemView.findViewById<TextView>(R.id.txtDiamond)
        val txtPrice = itemView.findViewById<TextView>(R.id.txtPrice)
    }

}