package com.lk.tasker

import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class splash_screen : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    lateinit var tasker : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
  //      val iconAnimation = AnimationDrawable()
//        iconAnimation.addFrame(resources.getDrawable(R.drawable.task),200)
//        iconAnimation.addFrame(resources.getDrawable(R.drawable.tasker_icon),200)
//        iconAnimation.addFrame(resources.getDrawable(R.drawable.message),200)
//        iconAnimation.addFrame(resources.getDrawable(R.drawable.bot),200)
//        iconAnimation.setOneShot(false);
//          iconImage.setBackgroundDrawable(iconAnimation)
 //         iconAnimation.start()
        val iconImage : ImageView = findViewById(R.id.imageView)
        iconImage.setBackgroundResource(R.drawable.icon_animation_list)

        val iconAnimation = iconImage.background
        if(iconAnimation is Animatable){
            iconAnimation.start()
        }
        val animDrawable = splash_screen_layout.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(2000)
        animDrawable.start()


        val stripesAnimation = AnimationUtils.loadAnimation(this,R.anim.strip_animation)
        stripes.startAnimation(stripesAnimation)

        Handler().postDelayed({

            startActivity(Intent(this,loginActivity::class.java))
        //    overridePendingTransition(R.anim.slide_in,R.anim.no_animation)
            finish()

        }, SPLASH_TIME_OUT)
    }

}
