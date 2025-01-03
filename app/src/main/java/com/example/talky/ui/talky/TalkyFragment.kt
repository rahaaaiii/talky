package com.example.talky.ui.talky

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.talky.ChatManager
import com.example.talky.R
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
                addMessageToChat("$userMessage", isUser = true)

                // Call ChatManager to send message
                chatManager.sendMessage(userMessage, { response ->
                    // Add the response to chatContainer with a sound button
                    addMessageToChat(response, isUser = false)
                    // Speak the response immediately using TTS
                    ttsManager.speak(response)
                }, { error ->
                    // Add error message to chatContainer
                    addMessageToChat("Error: $error", isUser = false)
                })
                // Clear input field after sending
                binding.inputField.text.clear()
            }
        }

        return binding.root
    }

    private fun addMessageToChat(message: String, isUser: Boolean) {
        val containerLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                // 사용자 메시지는 오른쪽, 봇 메시지는 왼쪽 정렬
                gravity = if (isUser) Gravity.END else Gravity.START
                setMargins(8, 8, 8, 8)
            }
        }

        val textView = TextView(requireContext()).apply {
            text = message
            setPadding(16, 8, 16, 8)
            setBackgroundResource(if (isUser) R.drawable.user_message_bubble else R.drawable.bot_message_bubble)

            // 동적 크기 조정을 위한 레이아웃 설정
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            // 최대 너비를 화면 크기의 75%로 제한
            maxWidth = (resources.displayMetrics.widthPixels * 0.75).toInt()
        }

        // 사용자 메시지는 오른쪽에만 추가
        if (isUser) {
            containerLayout.gravity = Gravity.END
        } else {
            containerLayout.gravity = Gravity.START
        }

        containerLayout.addView(textView)

        // 봇 메시지에만 소리 버튼 추가
        if (!isUser) {
            val soundButton = Button(requireContext()).apply {
                text = "🔊"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(8, 0, 0, 0) // 텍스트와 버튼 사이 여백
                }
                setOnClickListener {
                    // TTS로 메시지 읽기
                    ttsManager.speak(message)
                }
            }
            containerLayout.addView(soundButton)
        }

        binding.messagesContainer.addView(containerLayout)

        // 새 메시지가 추가되면 스크롤뷰를 아래로 스크롤
        binding.chatContainer.post {
            binding.chatContainer.fullScroll(View.FOCUS_DOWN)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ttsManager.shutdown() // Clean up TTS resources
    }
}
