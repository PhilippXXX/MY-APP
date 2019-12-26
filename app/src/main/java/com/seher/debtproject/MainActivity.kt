package com.seher.debtproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    var menu:Boolean = false
    private fun animateLowerPanel(right:Boolean) {

        if (right) {
            val leftToRight = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.right_anim
            )
            menuLayout.startAnimation(leftToRight)
            menuLayout.visibility = View.VISIBLE
        }
        else
        {
            val rightToLeft = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.left_anim
            )
            menuLayout.startAnimation(rightToLeft)
            menuLayout.visibility = View.INVISIBLE
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton.setOnClickListener {
            val intent = Intent(this, AddDebtActivity::class.java)
            startActivity(intent)

        }

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val currentDateandTime = sdf.format(Date())
        dateText.text = "Курс валют ($currentDateandTime):"
        button6.setOnClickListener {
            animateLowerPanel(menu)
            menu = !menu
        }
    }
}
