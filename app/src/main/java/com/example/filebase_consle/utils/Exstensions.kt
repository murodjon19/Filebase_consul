package com.example.filebase_consle.utils

import android.app.Activity
import android.widget.Toast

object Exstensions {

    fun Activity.toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}