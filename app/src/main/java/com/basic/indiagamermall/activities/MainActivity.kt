package com.basic.indiagamermall.activities

import android.R.id
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.basic.indiagamermall.BuildConfig
import com.basic.indiagamermall.R
import com.basic.indiagamermall.adapter.EventViewPagerAdapter
import com.basic.indiagamermall.adapter.StaggeredRecyclerGameInfoAdapter
import com.basic.indiagamermall.models.GameInfo
import com.google.android.material.navigation.NavigationView
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    var toolbar: Toolbar? = null
    lateinit var drawer: DrawerLayout
    private val drawerOpen = false
    val arrGameInfoList = ArrayList<GameInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()    // set toolbar
        initView() //initialization of views

        //To setup and manage auto scroll view pager...
        setupEventViewPager()

        //To fetch items to the arrGameInfoList
        fetchItems()
        //To setup recycler view
        setupRecyclerView()
    }

    fun initView(){
        try {
            //Side Menu views
            var txtMyOrder: TextView = findViewById(R.id.txtMyOrder)
            var txtBecomeReseller: TextView = findViewById(R.id.txtBecomeReseller)
            var txtRateUs: TextView = findViewById(R.id.txtRateUs)
            var txtShare: TextView = findViewById(R.id.txtShare)
            var txtSetting: TextView = findViewById(R.id.txtSetting)
            var txtHelpSupport: TextView = findViewById(R.id.txtHelpSupport)
            var txtLogout: TextView = findViewById(R.id.txtLogout)

            drawer = findViewById(R.id.drawer_layout)
            val toggle = ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer.addDrawerListener(toggle)
            toggle.syncState()
            val navigationView: NavigationView = findViewById(R.id.nav_view_dashboard)
            navigationView.setNavigationItemSelectedListener(this)
            onClickToSelf(Arrays.asList(txtMyOrder,txtBecomeReseller,txtRateUs, txtShare,txtSetting,
                    txtHelpSupport, txtLogout ), this)
        } catch (e: Exception) {
            Log.e("MainActivity" ,"InitView :  ", e)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer.closeDrawer(GravityCompat.START) //close navigation drawer
        return true
    }

    //click listener to side navigation tabs
    fun onClickToSelf(view: List<*>, onClickListener: View.OnClickListener?) {
        for (`object` in view) {
            (`object` as View).setOnClickListener(onClickListener)
        }
    }

    /**
     * Used to set toolbar
     */
    private fun setupToolbar() {
        try {
            toolbar = findViewById(R.id.toolbar)
            val txtToolBarName: TextView = findViewById(R.id.txtToolBarName)
            txtToolBarName.setText(R.string.app_name)
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("MainActivity", "setupToolbar: ", e)
        }
    }

    override fun onClick(v: View) {
        try {
            when (v.id) {
                R.id.txtBecomeReseller -> {
                    drawer.closeDrawer(GravityCompat.START)
                    val message = "Hiiii, I Want to become reseller On India Game Mall"
                    val phoneNumber = "917357678251"
                    // Calling the function
                    sendMessageViaWhatsapp(message,phoneNumber)
                }
                R.id.txtShare -> {
                    drawer.closeDrawer(GravityCompat.START)
                    // Calling the function
                    shareApp()
                }
                R.id.txtLogout -> {
                    drawer.closeDrawer(GravityCompat.START)
                }
                else -> {
                }
            }
        } catch (e: java.lang.Exception) {
           Log.e("MainActivity", "onClick: ", e)
        }
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

    fun shareApp() {

        // Creating intent with action send
        val intent = Intent(Intent.ACTION_SEND)
        // Setting Intent type
        intent.type = "text/plain"
        // Give your message here
        intent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id="
                        + BuildConfig.APPLICATION_ID)
        startActivity(Intent.createChooser(intent, "Share via"));
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