package com.seher.debtproject.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seher.debtproject.model.Main2Activity

class splashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, Main2Activity::class.java)
        startActivity(intent)
        finish()
    }
}