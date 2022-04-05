package com.example.filebase_consle.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.example.filebase_consle.AuthManager
import com.example.filebase_consle.R

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        intViews()
    }

    private fun intViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        val timer = object : CountDownTimer(2000,2000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                if (AuthManager.isSignedIn()) {
                    callMainActivity(this@SplashActivity)
                } else {
                    callSignInActivity(this@SplashActivity)
                }
            }
        }
        timer.start()
    }

    private fun openSignActivity() {
        val intent = Intent(this, SignInActivity::class.java);
        startActivity(intent)
        finish()
    }
}