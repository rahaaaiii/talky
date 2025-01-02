package com.example.talky

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Handler
import com.example.talky.auth.IntroActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
            finish()
        }, 3000)
    }
}