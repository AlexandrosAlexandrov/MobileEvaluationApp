package com.example.mobileevaluationproject

import SessionManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniapi.myAdapter
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class BooksActivity : AppCompatActivity() {

    lateinit var myAdapter: myAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        sessionManager = SessionManager(this)

        if(sessionManager.fetchAuthToken() == null){
            Toast.makeText(baseContext, "Wrong credentials, can't login.", Toast.LENGTH_SHORT).show()
            finish()}

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        getBooks()
    }

    private fun getBooks() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getBooks(token = "Bearer ${sessionManager.fetchAuthToken()}")

        retrofitData.enqueue(object : Callback<List<BooksItem>?>{
            override fun onResponse(
                call: Call<List<BooksItem>?>,
                response: Response<List<BooksItem>?>
            ) {
                val responseBody = response.body()

                Log.d("Books Activity", "BookListSize: " +responseBody?.size)

                myAdapter = myAdapter(baseContext, responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerView.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<BooksItem>?>, t: Throwable) {
                Log.d("Books Activity", "on Failure: " + t.message)
                Toast.makeText(baseContext, "Failed to log in ", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }


}