package com.rohan.speed_marquee_sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.rohan.speed_marquee.SpeedMarquee
import speed_marquee_sample.R

class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Example to Utilize pauseScroll() & resumeScroll()
         */
        val marqueeTextView = findViewById<SpeedMarquee>(R.id.marqueeTextView)

        marqueeTextView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    marqueeTextView.pauseScroll()
                }
                MotionEvent.ACTION_UP -> {
                    marqueeTextView.resumeScroll()
                }
            }
            return@OnTouchListener true
        })


        /**
         * Example to setSpeed()
         */
        val marqueeTextView3 = findViewById<SpeedMarquee>(R.id.marqueeTextView3)

        findViewById<Button>(R.id.speedButton).setOnClickListener {
            marqueeTextView3.setSpeed(marqueeTextView3.getSpeed() + 100.0f)
        }
    }
}