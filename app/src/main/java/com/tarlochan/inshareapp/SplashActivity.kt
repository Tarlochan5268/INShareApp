package com.tarlochan.inshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        //3second splash time
        Handler().postDelayed({
            //start main activity
            startActivity(Intent(this, MainActivity::class.java))
            //finish this activity
            finish()
        },3000)
    }
}