package com.lk.tasker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
 lateinit var toolbar :Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
         toolbar =
            findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)


    // drawer code
    @SuppressLint("CutPasteId")
    var drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

    @SuppressLint("CutPasteId")
    val holder = findViewById<LinearLayout>(R.id.holder)
    var toggle: ActionBarDrawerToggle =
        object : ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerSlide(
                drawerView: View,
                slideOffset: Float
            ) {
                val scaleFactor = 7f
                val slideX = drawerView.width * slideOffset
                holder.translationX = slideX
                holder.scaleX = 1 - slideOffset / scaleFactor
                holder.scaleY = 1 - slideOffset / scaleFactor
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }
        drawer.setDrawerElevation(0F);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
    {
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        ) // will remove all possible our aactivity's window bounds
    }

    drawer.addDrawerListener(toggle)
    drawer.setScrimColor(android.graphics.Color.TRANSPARENT)
    toggle.syncState()

    var navigationView =
        findViewById<View>(R.id.nav_view) as NavigationView
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true


            // Handle navigation view item clicks here.
            val id: String = menuItem.toString()
            Toast.makeText(baseContext,id,Toast.LENGTH_SHORT).show()

            drawer.closeDrawers()
            true
        }

    }

}
