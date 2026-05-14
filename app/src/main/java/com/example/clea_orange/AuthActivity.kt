package com.example.clea_orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clea_orange.databinding.ActivityLoginBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handling system bars padding for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Definisikan SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true (Pindahkan ke MainActivity jika sudah login)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // 2. Set isLogin menjadi true dan simpan username (email) jika berhasil
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", email)
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Tolong isi semua field", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
