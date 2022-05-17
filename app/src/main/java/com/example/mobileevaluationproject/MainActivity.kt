package com.example.mobileevaluationproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {

    lateinit var useridText: EditText
    lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //add text listener on userID and check the userID restrictions
        useridText = findViewById(R.id.useridText)
        useridText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(checkUserID(useridText.text.toString()))
                else {
                    useridText.error = "Λάθος userID"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        //add text listener on password and check password restrictions
        passwordText = findViewById(R.id.passwordText)
        passwordText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(checkPassword(passwordText.text.toString()))

                else passwordText.error = "Λάθος κωδικός"

            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    private fun checkUserID(text:String?): Boolean{
        var p:Pattern = Pattern.compile("[A-Z]{2}[0-9]{4}")
        var m:Matcher = p.matcher(text)
        return m.matches()
    }

    private fun checkPassword(text:String?):Boolean {
        var p:Pattern = Pattern.compile("^"
                +"(?=.*[0-9])"+"(?=.*[0-9])" //at least 2 numbers (most likely a better way to do it but this works)
                +"(?=.*[A-Z])"              //at least 1 uppercase letter
                +"(?=.*[a-z])"              //at least 1 lowercase letter
                +"(?=.*[@#$%^&*+=!])"       //at least 1 special character
                +".{8}"                     //8 characters
                +"$")
        var m:Matcher = p.matcher(text)
        return m.matches()
    }
}