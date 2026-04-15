package com.example.clea_orange.pertemuan3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dari LoginActivity
        val userName = intent.getStringExtra("USER_NAME")
        binding.tvWelcome.text = "Welcome My APP!!\n$userName"
    }
}