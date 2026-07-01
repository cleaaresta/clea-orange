package com.example.clea_orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        
        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        lifecycleScope.launch {
            delay(2000) // Splash screen tampil selama 2 detik
            
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val isFirstRun = sharedPref.getBoolean("firstRun", true)

            // PERBAIKAN: Cek Onboarding terlebih dahulu
            if (isFirstRun) {
                // Jika pertama kali buka aplikasi, tampilkan Onboarding
                val intent = Intent(this@SplashScreenActivity, OnboardingActivity::class.java)
                startActivity(intent)
            } else if (isLogin) {
                // Jika sudah pernah onboarding dan sudah login, ke Home
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
            } else {
                // Jika sudah pernah onboarding tapi belum login, ke Auth/Login
                val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}
