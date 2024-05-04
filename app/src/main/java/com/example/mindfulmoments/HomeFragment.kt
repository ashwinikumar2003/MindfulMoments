package com.example.mindfulmoments

import android.app.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var card_meditate: CardView
    private lateinit var card_breath: CardView
    private lateinit var start: Button
    private lateinit var meditationTimeText: TextView
    private lateinit var breathingTimeText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        meditationTimeText = view.findViewById(R.id.meditation_time_text)
        breathingTimeText = view.findViewById(R.id.breathing_time_text)
        card_meditate = view.findViewById(R.id.card_meditate)
        card_breath = view.findViewById(R.id.card_breath)
        start = view.findViewById(R.id.start)

        loadMeditationTime()
        loadBreathingTime()

        // Set click listeners for card views
        card_meditate.setOnClickListener {
            // Start MeditateActivity
            startActivity(Intent(requireContext(), MeditateActivity::class.java))
        }

        card_breath.setOnClickListener {
            // Start BreathActivity
            startActivity(Intent(requireContext(), BreathActivity::class.java))
        }

        // Set click listener for the start button
        start.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                // Call method to schedule the alarm with the selected time
                scheduleExactAlarm()
            },
            hour,
            minute,
            false
        )

        timePickerDialog.show()
    }

    private fun scheduleExactAlarm() {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Set the alarm time
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        try {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Toast.makeText(requireContext(), "Alarm scheduled for: ${calendar.time}", Toast.LENGTH_LONG).show()
        } catch (e: SecurityException) {
            // Handle the security exception
            Toast.makeText(requireContext(), "Failed to schedule alarm. Please try again later.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMeditationTime() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val totalMeditationMinutes = sharedPreferences.getInt("MeditationMinutes", 0)
        meditationTimeText.text = totalMeditationMinutes.toString()
    }

    private fun loadBreathingTime() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val totalBreathingMinutes = sharedPreferences.getInt("BreathingMinutes", 0)
        breathingTimeText.text = totalBreathingMinutes.toString()
    }

}
