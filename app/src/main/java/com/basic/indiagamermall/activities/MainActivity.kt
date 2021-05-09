package com.basic.indiagamermall.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.basic.indiagamermall.R
import com.basic.indiagamermall.adapter.EventViewPagerAdapter
import com.basic.indiagamermall.adapter.StaggeredRecyclerGameInfoAdapter
import com.basic.indiagamermall.models.GameInfo
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager

class MainActivity : AppCompatActivity() {

    val arrGameInfoList = ArrayList<GameInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To setup and manage auto scroll view pager...
        setupEventViewPager()

        //To fetch items to the arrGameInfoList
        fetchItems()
        //To setup recycler view
        setupRecyclerView()
    }

    fun fetchItems(){
        addItems(R.drawable.traffic_tour, "Traffic Tour")
        addItems(R.drawable.unicorn_runner, "Unicorn Runner")
        addItems(R.drawable.sumos, "Sumos")
        addItems(R.drawable.warzone, "WarZone")
        addItems(R.drawable.sumos, "Sumos")
        addItems(R.drawable.traffic_tour, "Traffic Tour")
        addItems(R.drawable.unicorn_runner, "Unicorn Runner")
        addItems(R.drawable.sumos, "Sumos")
        addItems(R.drawable.warzone, "WarZone")
        addItems(R.drawable.unicorn_runner, "Unicorn Runner")
        addItems(R.drawable.traffic_tour, "Traffic Tour")

    }

    //To add Items in the arrGameInfoList
    fun addItems(img: Int, name : String) {
        val gameInfo =  GameInfo(img, name)
        arrGameInfoList.add(gameInfo)
    }

    fun setupRecyclerView(){
        val recyclerGameInfo = findViewById<RecyclerView>(R.id.recyclerGameInfo)
        recyclerGameInfo.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)

        recyclerGameInfo.adapter = StaggeredRecyclerGameInfoAdapter(this,arrGameInfoList)
    }

    //funtion to manage event viewpager
    fun setupEventViewPager(){
        val eventViewPager: AutoScrollViewPager = findViewById(R.id.view_pager_events)
        var arrEvents: ArrayList<Int> = ArrayList()
        addEvents(arrEvents)
        var eventsAdapter: EventViewPagerAdapter = EventViewPagerAdapter(arrEvents, this)
        eventViewPager.adapter = eventsAdapter
        eventViewPager.startAutoScroll(1000)
    }

    //To add event to arrEvents Arraylist
    fun addEvents(arrEvents: ArrayList<Int>){
        arrEvents.add(R.drawable.gamer_logo)
        arrEvents.add(R.drawable.sumos)
        arrEvents.add(R.drawable.traffic_tour)
        arrEvents.add(R.drawable.unicorn_runner)
        arrEvents.add(R.drawable.warzone)
    }
}