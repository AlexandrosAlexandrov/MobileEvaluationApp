package com.example.mobileevaluationproject

import SessionManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Matcher
import java.util.regex.Pattern

const val BASE_URL = "https://3nt-demo-backend.azurewebsites.net/Access/"

class MainActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var useridText: EditText
    lateinit var passwordText: EditText
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)


        //add text listener on userID and check the userID restrictions
        useridText = findViewById(R.id.useridText)
        useridText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (checkUserID(useridText.text.toString()))
                else {
                    useridText.error = "Λάθος userID"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        //add text listener on password and check password restrictions
        passwordText = findViewById(R.id.passwordText)
        passwordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (checkPassword(passwordText.text.toString()))
                else {
                    passwordText.error = "Λάθος κωδικός"
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            if(checkUserID(useridText.text.toString()) && checkPassword(passwordText.text.toString())){
            login()
            Toast.makeText(this, "Logged in ", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BooksActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            this.startActivity(intent)}
            else{
                Toast.makeText(this, "Wrong ID / Password ", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun checkUserID(text: String?): Boolean {
        var p: Pattern =
            Pattern.compile("[A-Z]{2}[0-9]{4}") //2 uppercase letters followed by 4 numbers
        var m: Matcher = p.matcher(text)
        return m.matches()
    }

    private fun checkPassword(text: String?): Boolean {
        var p: Pattern = Pattern.compile(
            "^"
                    + "(?=.*[0-9])" + "(?=.*[0-9])"                //at least 2 numbers (most likely a better way to do it but this works)
                    + "(?=.*[A-Z])" + "(?=.*[A-Z])"                //at least 2 uppercase letters
                    + "(?=.*[a-z])" + "(?=.*[a-z])" + "(?=.*[a-z])"  //at least 3 lowercase letters
                    + "(?=.*[@#$%^&*+=!])"                       //at least 1 special character
                    + ".{8}"                                     //8 characters
                    + "$"
        )
        var m: Matcher = p.matcher(text)
        return m.matches()
    }

    private fun login() {

        val myLogin = LoginFields(passwordText.text.toString(), useridText.text.toString())

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.Login(myLogin)

        retrofitData.enqueue(object : Callback<Token?> {
            override fun onResponse(call: Call<Token?>, response: Response<Token?>) {
                val responseBody = response.body()!!

                sessionManager.saveAuthToken(responseBody.access_token)
            }

            override fun onFailure(call: Call<Token?>, t: Throwable) {
                Log.d("Main Activity", "on Failure: " + t.message)
            }
        })

    }
}