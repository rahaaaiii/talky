package com.example.talky

import com.example.talky.api.ChatRequest
import com.example.talky.api.Message
import com.example.talky.api.RetrofitClient
import com.example.talky.api.ChatResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatManager {
    private val history = mutableListOf<Message>()

    fun sendMessage(userMessage: String, onResult: (String) -> Unit, onError: (String) -> Unit) {
        val systemMessage = Message(
            role = "system",
            content = """
                너의 이름은 'Talky'야.
                너는 사용자의 나이에 맞춰 또래 나이로 자신을 소개해.
                사용자의 말을 최대한 듣고 잘 공감하는 대화 친구가 되어줘.
                사용자가 명시적으로 대화를 끝내고 싶어할 때까지 계속 사용자와의 대화를 이어가.

                주의 사항:
                - 추상적인 개념이나 관용구 사용 자제
                - 사용자에게 욕설, 어려운 용어 등의 사용 자제
                - 말하는 의도를 자세히 설명
            """.trimIndent()
        )
        if (history.isEmpty()) history.add(systemMessage)

        val userMessageObj = Message(role = "user", content = userMessage)
        history.add(userMessageObj)

        val request = ChatRequest(messages = history)
        RetrofitClient.apiService.getChatCompletion(request).enqueue(object : Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    val assistantMessage = response.body()?.choices?.get(0)?.message?.content
                    assistantMessage?.let { message ->
                        history.add(Message(role = "assistant", content = message))
                        onResult(message)
                    } ?: onError("No response content")
                } else {
                    onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                onError("Failed to connect: ${t.message}")
            }
        })
    }
}
