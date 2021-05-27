package com.basic.indiagamermall.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
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

    }

    class GameSchemeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}