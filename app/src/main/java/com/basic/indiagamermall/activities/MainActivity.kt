package com.basic.indiagamermall.activities

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
import com.basic.indiagamermall.models.EventModel
import com.basic.indiagamermall.models.CategoryModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    lateinit var auth: FirebaseAuth
    var toolbar: Toolbar? = null
    lateinit var drawer: DrawerLayout
    private val drawerOpen = false
    val arrGameInfoList = ArrayList<CategoryModel>()
    lateinit var fltUploadEventBtn: FloatingActionButton
    var arrEventImages: ArrayList<String> = ArrayList()
    var arrEvents: ArrayList<EventModel> = ArrayList()
    lateinit var eventsAdapter: EventViewPagerAdapter
    lateinit var fltUploadCategoryBtn: FloatingActionButton
    var arrProductCatgories: ArrayList<CategoryModel> = ArrayList()
    lateinit var categoryAdapter: StaggeredRecyclerGameInfoAdapter

    lateinit var eventFireStore: FirebaseFirestore
    lateinit var categoryFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()    // set toolbar
        initView() //initialization of views

        //To initialize firebase variables
        initFirebase()

        //To get event data from Firestore
        getEventDataFS()

        //To get product category data from firestore
        getProductCategoryDataFS()

        //To setup and manage auto scroll view pager...
        setupEventViewPager()

        //To fetch items to the arrGameInfoList
        //fetchItems()
        //To setup recycler view
        setupRecyclerView()

    }

    fun initView(){
        auth = Firebase.auth
        fltUploadEventBtn = findViewById(R.id.flt_add_events)
        fltUploadEventBtn.setOnClickListener(this)
        fltUploadCategoryBtn = findViewById(R.id.flt_add_category)
        fltUploadCategoryBtn.setOnClickListener(this)
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

    private fun initFirebase(){
        eventFireStore = FirebaseFirestore.getInstance()
        categoryFirestore = FirebaseFirestore.getInstance()
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
                    Firebase.auth.signOut()
                    startLoginActivity()
                }
                R.id.flt_add_events -> {
                    startEventUploadActivity()
                }
                R.id.flt_add_category -> {
                    startCategoryUploadActivity()
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
        addItems("", "Traffic Tour", "")
        addItems("", "Unicorn Runner", "")
        addItems("", "Sumos", "")
        addItems("", "WarZone", "")
        addItems("", "Sumos", "")
        addItems("", "Traffic Tour", "")
        addItems("", "Unicorn Runner", "")
        addItems("", "Sumos", "")
        addItems("", "WarZone", "")
        addItems("", "Unicorn Runner", "")
        addItems("", "Traffic Tour", "")

    }

    //To add Items in the arrGameInfoList
    fun addItems(img: String, category : String, key: String) {
        val gameInfo =  CategoryModel(img, category, key)
        arrGameInfoList.add(gameInfo)
    }

    fun setupRecyclerView(){
        val recyclerGameInfo = findViewById<RecyclerView>(R.id.recyclerGameInfo)
        recyclerGameInfo.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)

        categoryAdapter = StaggeredRecyclerGameInfoAdapter(this, arrProductCatgories)
        recyclerGameInfo.adapter = categoryAdapter
    }

    //funtion to manage event viewpager
    fun setupEventViewPager(){
        val eventViewPager: AutoScrollViewPager = findViewById(R.id.view_pager_events)

        //addEvents(arrEvents)
        eventsAdapter = EventViewPagerAdapter(arrEventImages, this)
        eventViewPager.adapter = eventsAdapter
        eventViewPager.startAutoScroll(1000)
        eventViewPager.setOnClickListener {

            Toast.makeText(applicationContext, "category:" +arrEvents.get(it.id).categoryName , Toast.LENGTH_SHORT).show()

        }
    }

    //To add event to arrEvents Arraylist
    fun addEvents(arrEvents: ArrayList<Int>){
        arrEvents.add(R.drawable.gamer_logo)
        arrEvents.add(R.drawable.sumos)
        arrEvents.add(R.drawable.traffic_tour)
        arrEvents.add(R.drawable.unicorn_runner)
        arrEvents.add(R.drawable.warzone)
    }

    //To call login activity intent
    private fun startLoginActivity(){
        var iLogin: Intent = Intent(applicationContext, LoginActivity:: class.java)
        startActivity(iLogin)
        finish()
    }

    private fun startEventUploadActivity(){
        var iUploadEvent: Intent = Intent(applicationContext, EventUploadActivity:: class.java)
        startActivity(iUploadEvent)
    }

    private fun getEventDataFS(){
        eventFireStore.collection("EventCategories").addSnapshotListener { snapshot, e->
            val dataId: String
            for (dataId in snapshot?.documents!!){
                val eventModel: EventModel = EventModel(
                    dataId.getString("ImgUrl").toString(),
                    dataId.getString("Category").toString(),
                dataId.getString("Key").toString())

                arrEvents.add(eventModel)
                //Toast.makeText(applicationContext, dataId.getString("Category"), Toast.LENGTH_SHORT).show()

                try {
                    dataId.getString("ImgUrl")?.let { arrEventImages.add(it) }
                    eventsAdapter.notifyDataSetChanged()

                }catch (e: Exception){

                }
            }

        }
    }

    //To get product category data from firestore
    private fun getProductCategoryDataFS(){
        categoryFirestore.collection("ProductCategories").addSnapshotListener { value, error ->

            for (categoryDataId in value?.documents!!)  {
                val categoryModel: CategoryModel = CategoryModel(
                        categoryDataId.getString("ImgUrl").toString(),
                        categoryDataId.getString("Category").toString(),
                        categoryDataId.getString("Key").toString())

                arrProductCatgories.add(categoryModel)
                categoryAdapter.notifyDataSetChanged()
            //Toast.makeText(applicationContext, categoryDataId.getString("Category"), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCategoryUploadActivity(){
        val iCatUpload: Intent = Intent(applicationContext, CategoryUploadActivity:: class.java)
        startActivity(iCatUpload)
    }
}