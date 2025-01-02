package com.example.talky.auth

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.talky.ChatActivity
import com.example.talky.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val ageEditText = findViewById<EditText>(R.id.ageEditText)

        val languageSpinner = findViewById<Spinner>(R.id.languageSpinner)
        val languages = listOf("한국어", "English", "Español", "Français", "Deutsch")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString()
            val selectedLanguage = languageSpinner.selectedItem.toString()

            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("USER_NAME", name)
                putExtra("USER_AGE", age)
                putExtra("SELECTED_LANGUAGE", selectedLanguage)
            }
            startActivity(intent)
        }
    }
}
