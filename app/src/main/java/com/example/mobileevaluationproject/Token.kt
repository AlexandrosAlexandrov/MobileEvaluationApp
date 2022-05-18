package com.example.mobileevaluationproject

data class Token(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)