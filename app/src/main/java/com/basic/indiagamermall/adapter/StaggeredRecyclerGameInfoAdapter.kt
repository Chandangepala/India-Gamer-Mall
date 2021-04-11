package com.basic.indiagamermall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
import com.basic.indiagamermall.models.GameInfo

class StaggeredRecyclerGameInfoAdapter(private val arrGameInfoList: ArrayList<GameInfo>)
    :RecyclerView.Adapter<StaggeredRecyclerGameInfoAdapter.GameInfoViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameInfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item_card, parent,
            false);
        return GameInfoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return arrGameInfoList.size
    }

    override fun onBindViewHolder(holder: GameInfoViewHolder, position: Int) {
        holder.imgGame.setImageResource(arrGameInfoList.get(position).gameImage)
        holder.txtGameName.setText(arrGameInfoList.get(position).gameName)
    }

    class GameInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgGame = itemView.findViewById<ImageView>(R.id.imgGame);
        val txtGameName = itemView.findViewById<TextView>(R.id.txtGameName)
    }
}