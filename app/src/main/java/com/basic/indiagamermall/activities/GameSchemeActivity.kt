package com.basic.indiagamermall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
import com.basic.indiagamermall.adapter.RecyclerGameSchemeAdapter
import com.basic.indiagamermall.models.ProductModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameSchemeActivity : AppCompatActivity() {

    lateinit var fltUploadProduct: FloatingActionButton
    val arrGameSchemeList = ArrayList<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scheme)

        initMain()

        //To setup recycler view
        setupRecyclerView()
    }

    fun initMain(){
        fltUploadProduct = findViewById(R.id.flt_add_product_btn)

    }
    fun setupRecyclerView(){
        val recyclerGameScheme = findViewById<RecyclerView>(R.id.recyclerGameScheme)
        recyclerGameScheme.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        recyclerGameScheme.adapter = RecyclerGameSchemeAdapter(arrGameSchemeList, this)
    }
}