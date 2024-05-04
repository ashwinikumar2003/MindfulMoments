package com.example.mindfulmoments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class BreathActivity : AppCompatActivity() {

    private lateinit var animationView: LottieAnimationView
    private lateinit var startButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var countdownTimer: CountDownTimer

    private var isBreathing = false
    private var breathingMinutes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breath)

        animationView = findViewById(R.id.animationView2)
        startButton = findViewById(R.id.start2)
        timerTextView = findViewById(R.id.timer_counter_breath)
        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_sound)

        startButton.setOnClickListener {
            if (isBreathing) {
                stopBreathing()
                saveBreathingMinutes()
            } else {
                startBreathing()
            }
        }
    }

    private fun startBreathing() {
        isBreathing = true
        startButton.text = "Stop"
        animationView.playAnimation()
        mediaPlayer.start()

        countdownTimer = object : CountDownTimer(180000, 1000) { // 3 minutes countdown
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                stopBreathing()
                saveBreathingMinutes()
            }
        }.start()
    }

    private fun stopBreathing() {
        isBreathing = false
        startButton.text = "Start"
        animationView.pauseAnimation()
        mediaPlayer.pause()
        countdownTimer.cancel()
        timerTextView.text = "03:00"
    }

    private fun saveBreathingMinutes() {
        breathingMinutes += 3
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("BreathingMinutes", breathingMinutes)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

