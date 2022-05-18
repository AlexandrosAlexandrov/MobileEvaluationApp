package com.example.mobileevaluationproject

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {
    @POST("Login")
    fun Login(@Body login: LoginFields):Call<Token>

    @GET("Books")
    fun getBooks(@Header("Authorization:") token: String): Call<List<BooksItem>>
    }
