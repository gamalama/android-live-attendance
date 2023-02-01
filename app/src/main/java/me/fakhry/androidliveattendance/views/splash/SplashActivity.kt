package me.fakhry.androidliveattendance.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import me.fakhry.androidliveattendance.R
import me.fakhry.androidliveattendance.hawkstorage.HawkStorage
import me.fakhry.androidliveattendance.views.login.LoginActivity
import me.fakhry.androidliveattendance.views.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        afterDelayGoToLogin()
    }

    private fun afterDelayGoToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            checkIfLogin()
        }, 1200)
    }

    private fun checkIfLogin() {
        val isLogin = HawkStorage.instance(this).isLogin()
        if (isLogin) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}