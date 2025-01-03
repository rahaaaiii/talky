package com.example.talky.model

class ApiRequest {
    private var userInput: String? = null


    fun getUserInput(): String? {
        return userInput
    }

    fun setUserInput(userInput: String?) {
        this.userInput = userInput
    }
}