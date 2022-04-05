package com.example.filebase_consle.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.filebase_consle.AuthHandler
import com.example.filebase_consle.AuthManager
import com.example.filebase_consle.R
import com.example.filebase_consle.utils.Exstensions.toast

class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    fun initViews() {
        et_fullname = findViewById(R.id.et_fullName)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        val b_signup = findViewById<Button>(R.id.btn_signup)
        b_signup.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            firebaseSignUp(email, password)
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener { callSignInActivity(this) }
    }

    fun firebaseSignUp(email: String, password: String) {
        //showLoading(this)
        AuthManager.signUp(email, password, object : AuthHandler {
            override fun onSuccess() {
                toast("Signed up successfully")
                //dismissLoading()
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                //dismissLoading()
                toast("Sign up failed")
            }
        })
    }
}