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
        val selectedLanguage = intent.getStringExtra("SELECTED_LANGUAGE") ?: "한국어"

        val prompt = "사용자 이름: $userName , 나이: $userAge , 사용자 선택 언어: $selectedLanguage . 이 정보를 바탕으로 대화를 시작."
        startChatWithGPT(prompt)
    }

    private fun startChatWithGPT(prompt: String) {
        // GPT 대화 구현
    }
}
