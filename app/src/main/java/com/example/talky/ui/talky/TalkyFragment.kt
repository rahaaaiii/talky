package com.example.talky.ui.talky

import ChatViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.talky.R
import com.example.talky.databinding.FragmentTalkyBinding

class TalkyFragment : Fragment() {

    private var _binding: FragmentTalkyBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by activityViewModels()  // 여기서 ViewModel을 가져옵니다.

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

        loadChatMessages()

        binding.sendButton.setOnClickListener {
            val message = binding.inputField.text.toString()
            if (message.isNotBlank()) {
                chatViewModel.addMessage("User", message)
                addMessageToChat("You: $message", true)
                binding.inputField.text.clear()

                val botResponse = "Bot: Thank you for your message!"
                chatViewModel.addMessage("Bot", botResponse)
                addMessageToChat(botResponse, false)
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

        // 메시지 추가
        binding.messagesContainer.addView(textView)

        binding.chatContainer.post {
            binding.chatContainer.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun loadChatMessages() {
        for (chatMessage in chatViewModel.chatMessages) {
            addMessageToChat("${chatMessage.sender}: ${chatMessage.message}", chatMessage.sender == "User")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}