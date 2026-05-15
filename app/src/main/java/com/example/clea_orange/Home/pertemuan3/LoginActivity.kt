package com.example.clea_orange.Home.pertemuan3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityLoginBinding
import com.example.clea_orange.Home.pertemuan4.DashboardActivity
import com.example.clea_orange.GmailInputActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val inputIdentifier = binding.etEmail.text.toString().trim()
            val inputPassword = binding.etPassword.text.toString().trim()

            // Ambil data yang tersimpan di SharedPreferences saat registrasi
            val registeredUsername = sharedPref.getString("username", null)
            val registeredPassword = sharedPref.getString("password", null)
            val registeredEmail = sharedPref.getString("email", null)

            if (inputIdentifier.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Tolong isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi: Input harus cocok dengan Username ATAU Email, dan Password harus cocok
            if ((inputIdentifier == registeredUsername || inputIdentifier == registeredEmail) && inputPassword == registeredPassword) {
                // Simpan status login
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username/Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        // Hubungkan juga tombol Register Gmail jika ada di layout ini
        binding.btnRegisterGmail.setOnClickListener {
            val intent = Intent(this, GmailInputActivity::class.java)
            startActivity(intent)
        }
    }
}
