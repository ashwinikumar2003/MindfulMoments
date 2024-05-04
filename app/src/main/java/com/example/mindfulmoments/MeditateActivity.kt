package com.example.mindfulmoments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class MeditateActivity : AppCompatActivity() {

    private lateinit var animationView: LottieAnimationView
    private lateinit var startButton: Button
    private lateinit var timerTextView: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var countdownTimer: CountDownTimer

    private var isMeditating = false
    private var meditationMinutes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditate)

        animationView = findViewById(R.id.animationView1)
        startButton = findViewById(R.id.start1)
        timerTextView = findViewById(R.id.timer_counter_meditate)

        mediaPlayer = MediaPlayer.create(this, R.raw.meditation_sound)

        startButton.setOnClickListener {
            if (isMeditating) {
                stopMeditation()
            } else {
                startMeditation()
            }
        }
    }

    private fun startMeditation() {
        isMeditating = true
        startButton.text = "Stop"
        animationView.playAnimation()
        mediaPlayer.start()

        countdownTimer = object : CountDownTimer(600000, 1000) { // 10 minutes countdown
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                stopMeditation()
                saveMeditationMinutes()
            }
        }.start()
    }

    private fun stopMeditation() {
        isMeditating = false
        startButton.text = "Start"
        animationView.pauseAnimation()
        mediaPlayer.pause()
        countdownTimer.cancel()
        timerTextView.text = "10:00"
    }

    private fun saveMeditationMinutes() {
        meditationMinutes += 10
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("MeditationMinutes", meditationMinutes)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
