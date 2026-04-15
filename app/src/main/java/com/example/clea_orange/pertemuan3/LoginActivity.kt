package com.example.clea_orange.pertemuan3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Inisialisasi ViewBinding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Berpindah ke WelcomeActivity
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.putExtra("USER_NAME", username) // Mengirim data nama
                startActivity(intent)
            } else {
                Toast.makeText(this, "Tolong isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }
}