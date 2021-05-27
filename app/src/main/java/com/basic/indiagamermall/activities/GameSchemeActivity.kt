package com.basic.indiagamermall.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basic.indiagamermall.R
import com.basic.indiagamermall.adapter.RecyclerGameSchemeAdapter
import com.basic.indiagamermall.models.ProductModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GameSchemeActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var fltUploadProduct: FloatingActionButton
    val arrGameSchemeList = ArrayList<ProductModel>()
    lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_scheme)

        initMain()

        //To setup recycler view
        setupRecyclerView()
    }

    fun initMain(){
        category = intent.getStringExtra("categoryName").toString()
        fltUploadProduct = findViewById(R.id.flt_add_product_btn)
        fltUploadProduct.setOnClickListener(this)
    }
    fun setupRecyclerView(){
        val recyclerGameScheme = findViewById<RecyclerView>(R.id.recyclerGameScheme)
        recyclerGameScheme.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        recyclerGameScheme.adapter = RecyclerGameSchemeAdapter(arrGameSchemeList, this)
    }

    fun sendMessageViaWhatsapp(message: String, phoneNumber: String) {
        // send message to a number via whatsapp
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.putExtra("jid", phoneNumber.toString() + "@s.whatsapp.net")
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.setPackage("com.whatsapp")
            sendIntent.type = "text/plain"
            if (intent.resolveActivity(packageManager) == null) {
                Toast.makeText(this,
                    "Please install whatsapp first.",
                    Toast.LENGTH_SHORT).show()
                return
            }
            // Starting Whatsapp
            startActivity(sendIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.flt_add_product_btn -> {
                startProductUploadActivity()
            }
        }
    }

    //To call intent of product upload activity
    private fun startProductUploadActivity(){
        val iProductUpload: Intent = Intent(applicationContext, ProductUploadActivity :: class.java)
        iProductUpload.putExtra("categoryName", category)
        startActivity(iProductUpload)
    }
}