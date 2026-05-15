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

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Jika sudah login, langsung ke BaseActivity yang berisi BottomNavigation
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val inputIdentifier = binding.etEmail.text.toString()
            val inputPassword = binding.etPassword.text.toString()

            // Mengambil data yang tersimpan saat registrasi
            val registeredUsername = sharedPref.getString("username", null)
            val registeredPassword = sharedPref.getString("password", null)
            val registeredEmail = sharedPref.getString("email", null)

            if (inputIdentifier.isEmpty() || inputPassword.isEmpty()) {
                Toast.makeText(this, "Tolong isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi: Input harus cocok dengan Username ATAU Email, dan Password harus cocok
            if ((inputIdentifier == registeredUsername || inputIdentifier == registeredEmail) && inputPassword == registeredPassword) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                // Kita simpan username yang sedang aktif login
                editor.putString("current_user", registeredUsername)
                editor.apply()

                val intent = Intent(this, BaseActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username/Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRegisterGmail.setOnClickListener {
            val intent = Intent(this, GmailInputActivity::class.java)
            startActivity(intent)
        }
    }
}
