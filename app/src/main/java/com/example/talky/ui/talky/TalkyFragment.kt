package com.example.talky.ui.talky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.talky.R
import com.example.talky.databinding.FragmentTalkyBinding

class TalkyFragment : Fragment() {

    private var _binding: FragmentTalkyBinding? = null
    private val binding get() = _binding!!

    private val chatMessages = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTalkyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up Send Button click listener
        binding.sendButton.setOnClickListener {
            val message = binding.inputField.text.toString()
            if (message.isNotBlank()) {
                addMessageToChat("You: $message", true)  // User message
                binding.inputField.text.clear()

                // Simulate a response from the bot
                addMessageToChat("Bot: Thank you for your message!", false)  // Bot message
            }
        }
    }

    private fun addMessageToChat(message: String, isUser: Boolean) {
        val textView = TextView(requireContext()).apply {
            text = message
            setPadding(16, 8, 16, 8)

            if (isUser) {
                setBackgroundResource(R.drawable.user_message_bubble)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = android.view.Gravity.END
                }
            } else {
                setBackgroundResource(R.drawable.bot_message_bubble)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = android.view.Gravity.START
                }
            }
        }

        binding.messagesContainer.addView(textView)

        binding.chatContainer.post {
            binding.chatContainer.fullScroll(View.FOCUS_DOWN)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}