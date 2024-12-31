package com.example.talky.ui.talky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.talky.ChatManager
import com.example.talky.TTSManager
import com.example.talky.databinding.FragmentTalkyBinding

class TalkyFragment : Fragment() {

    private var _binding: FragmentTalkyBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatManager: ChatManager
    private lateinit var ttsManager: TTSManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTalkyBinding.inflate(inflater, container, false)

        // Initialize ChatManager and TTSManager
        chatManager = ChatManager()
        ttsManager = TTSManager(requireContext())

        // Set up UI interactions
        binding.sendButton.setOnClickListener {
            val userMessage = binding.inputField.text.toString()
            if (userMessage.isNotEmpty()) {
                // Display user message on the screen
                addMessageToChat("You: $userMessage")

                // Call ChatManager to send message
                chatManager.sendMessage(userMessage, { response ->
                    // Add the response to chatContainer with a sound button
                    addResponseToChat(response)
                    // Speak the response immediately using TTS
                    ttsManager.speak(response)
                }, { error ->
                    // Add error message to chatContainer
                    addResponseToChat("Error: $error")
                })
                // Clear input field after sending
                binding.inputField.text.clear()
            }
        }

        return binding.root
    }

    private fun addMessageToChat(message: String) {
        // Create a TextView for the user message
        val userMessageText = TextView(requireContext()).apply {
            text = message
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 8, 8, 8)
            }
        }

        // Add the user message TextView to the chatContainer
        binding.chatContainer.addView(userMessageText)
    }

    private fun addResponseToChat(response: String) {
        // Create a horizontal layout for the response and button
        val responseLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Create a TextView for the response
        val responseText = TextView(requireContext()).apply {
            text = response
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f // Weight for expanding to fill space
            )
        }

        // Create a Button for replaying the response
        val soundButton = Button(requireContext()).apply {
            text = "🔊"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                // Speak the response using TTS
                ttsManager.speak(response)
            }
        }

        // Add the TextView and Button to the horizontal layout
        responseLayout.addView(responseText)
        responseLayout.addView(soundButton)

        // Add the response layout to the chatContainer
        binding.chatContainer.addView(responseLayout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ttsManager.shutdown() // Clean up TTS resources
    }
}
