package com.example.mindfulmoments

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import android.Manifest
import android.widget.Toast
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class UserFragment : Fragment() {

    private lateinit var textWeeklyProgress: TextView
    private lateinit var textWeeklyProgressValue: TextView
    private lateinit var progressBarWeekly: ProgressBar
    private lateinit var buttonMarkStreak: Button
    private lateinit var calendarView: CalendarView
    lateinit var fstream: FileOutputStream

    private lateinit var sharedPreferences1: SharedPreferences
    private val filename = "SampleFile.txt"
    private val filepath = "MyFileStorage"
    lateinit var myExternalFile: File
    private var count = "0"
    var mPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        // Initialize views


        sharedPreferences1 = requireContext().getSharedPreferences("WeekRecord ", Context.MODE_PRIVATE)

        textWeeklyProgress = view.findViewById(R.id.text_weekly_progress)
        textWeeklyProgressValue = view.findViewById(R.id.text_weekly_progress_value)
        progressBarWeekly = view.findViewById(R.id.progress_bar_weekly)
        buttonMarkStreak = view.findViewById(R.id.button_mark_streak)
        calendarView = view.findViewById(R.id.calendar_view)



        // Set button click listener to mark streak
        buttonMarkStreak.setOnClickListener {
            val CurrCount = sharedPreferences1.getInt(count, 0)
            val newCorrectCount = CurrCount + 1
            saveCorrect(newCorrectCount%7)
            updateStreakDisplay(newCorrectCount.toInt())
            try {

                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        mPermission[0]
                    ) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        mPermission[1]
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(requireActivity(), mPermission, 1001)
                } else {
                    Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
                    myExternalFile = File(requireContext().getExternalFilesDir(filepath), filename)
                    fstream = FileOutputStream(myExternalFile)

                    val correctCount = sharedPreferences1.getInt(count, 0)
                    fstream.write(correctCount.toString().toByteArray())
                    fstream.close()
                }
            }
            catch (e: FileNotFoundException) {
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()

                e.printStackTrace()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

        }

        // Set CalendarView to today's date
        calendarView.date = System.currentTimeMillis()

        return view
    }

    private fun saveCorrect(counter: Int) {
        val editor = sharedPreferences1.edit()
        editor.putInt(count, counter)
        editor.apply()
    }

    private fun updateStreakDisplay(streakCount: Int) {
        // Update TextView and ProgressBar with streak information
        textWeeklyProgressValue.text = "$streakCount times this week"
        progressBarWeekly.progress = (streakCount % 7)*(100/7)
    }
}


