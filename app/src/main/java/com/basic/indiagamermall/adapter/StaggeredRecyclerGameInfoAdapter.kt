package com.basic.indiagamermall.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
import com.basic.indiagamermall.activities.GameSchemeActivity
import com.basic.indiagamermall.activities.MainActivity
import com.basic.indiagamermall.models.GameInfo

class StaggeredRecyclerGameInfoAdapter(val context: Context,private val arrGameInfoList: ArrayList<GameInfo>)
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
        holder.linearLayoutGame.setOnClickListener{
            val iGameSchemeActivity = Intent(context, GameSchemeActivity :: class.java)
            context.startActivity(iGameSchemeActivity)
        }
    }

    class GameInfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imgGame = itemView.findViewById<ImageView>(R.id.imgGame)
        val txtGameName = itemView.findViewById<TextView>(R.id.txtGameName)
        val linearLayoutGame = itemView.findViewById<LinearLayout>(R.id.linearLayoutGame)
    }
}