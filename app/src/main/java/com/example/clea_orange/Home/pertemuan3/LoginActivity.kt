package com.example.clea_orange.Home.pertemuan3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityLoginBinding
import com.example.clea_orange.Home.pertemuan4.DashboardActivity

class LoginActivity : AppCompatActivity() {

    // Inisialisasi ViewBinding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // Menyesuaikan dengan ID baru 'etEmail' dari layout yang telah diperbarui
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
            } else {
                Toast.makeText(this, "Tolong isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
