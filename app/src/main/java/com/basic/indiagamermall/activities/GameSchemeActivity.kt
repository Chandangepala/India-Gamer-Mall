package com.basic.indiagamermall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.basic.indiagamermall.R
import com.basic.indiagamermall.adapter.RecyclerGameSchemeAdapter
import com.basic.indiagamermall.adapter.StaggeredRecyclerGameInfoAdapter
import com.basic.indiagamermall.models.GameInfo
import com.basic.indiagamermall.models.GameSchemeResponce

class GameSchemeActivity : AppCompatActivity() {

    val arrGameSchemeList = ArrayList<GameSchemeResponce>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scheme)

        //To setup recycler view
        setupRecyclerView()
    }

    fun setupRecyclerView(){
        val recyclerGameScheme = findViewById<RecyclerView>(R.id.recyclerGameScheme)
        recyclerGameScheme.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        recyclerGameScheme.adapter = RecyclerGameSchemeAdapter(arrGameSchemeList)
    }
}