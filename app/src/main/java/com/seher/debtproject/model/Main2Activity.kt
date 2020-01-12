package com.seher.debtproject.model


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.seher.debtproject.view.MainActivityController
import com.seher.debtproject.R
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.nav_header_main2.*
import java.text.SimpleDateFormat
import java.util.*


class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private val controller =
        MainActivityController(this)

    override fun onResume() {
        super.onResume()
        if (myDebtText != null)
            controller.getStats()
        controller.setup(-1)
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.destroy()
    }


    private fun setNavigationViewListener() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.nav_today -> {
                controller.setup(0)
            }
            R.id.nav_me -> {
                controller.setup(1)
            }
            R.id.nav_tome -> {
                controller.setup(2)
            }
        }
        //close navigation drawer

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Где деньги"
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setNavigationViewListener()




        addButton.setOnClickListener {
            controller.add()
        }

        controller.setupSwipe()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)


        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDateandTime = sdf.format(Date())


        dateText.text = "Курс валют $currentDateandTime"

        controller.getStats()
        RetrofitAPI.getMoney(this, dollarText, euroText)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
