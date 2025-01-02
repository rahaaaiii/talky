package com.example.talky.auth

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.talky.R
import com.example.talky.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_intro)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        binding.profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.langBtn.setOnClickListener {
            val intent = Intent(this, LangActivity::class.java)
            startActivity(intent)
        }
    }
}