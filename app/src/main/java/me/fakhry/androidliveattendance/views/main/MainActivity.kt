package me.fakhry.androidliveattendance.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import me.fakhry.androidliveattendance.R
import me.fakhry.androidliveattendance.databinding.ActivityMainBinding
import me.fakhry.androidliveattendance.views.attendance.AttendanceFragment
import me.fakhry.androidliveattendance.views.history.HistoryFragment
import me.fakhry.androidliveattendance.views.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.btmNavigationMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_history -> {
                    openFragment(HistoryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_attendance -> {
                    openFragment(AttendanceFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_profile -> {
                    openFragment(ProfileFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        binding.btmNavigationMain.selectedItemId = R.id.action_attendance
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}