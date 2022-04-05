package com.example.filebase_consle

interface AuthHandler {
    fun onSuccess()
    fun onError(exception: Exception?)
}
