package com.example.talky

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        val userName = intent.getStringExtra("USER_NAME") ?: "Unknown"
        val userAge = intent.getStringExtra("USER_AGE") ?: "Unknown"
        
        val prompt = "사용자의 이름은 $userName 이며 나이는 $userAge 입니다. 이 정보를 바탕으로 대화를 시작하세요."
        ㅇ
        startChatWithGPT(prompt)
    }
    
    private fun startChatWithGPT(prompt:String) {
        // GPT API를 사용한 대화 구현
    }
}