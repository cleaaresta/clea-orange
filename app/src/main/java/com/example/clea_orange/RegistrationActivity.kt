package com.example.clea_orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityRegistrationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        binding.etRegEmail.setText(email)

        binding.btnDoRegister.setOnClickListener {
            val nama = binding.etRegNama.text.toString().trim()
            val username = binding.etRegUsername.text.toString().trim()
            val password = binding.etRegPassword.text.toString().trim()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showErrorDialog("Semua field wajib diisi")
            } else if (password.length < 6) {
                showErrorDialog("Password minimal 6 karakter")
            } else if (username.contains(" ")) {
                showErrorDialog("Username tidak boleh mengandung spasi")
            } else {
                saveRegistrationData(nama, email, username, password)
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveRegistrationData(nama: String, email: String, username: String, pass: String) {
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("nama", nama)
            putString("email", email)
            putString("username", username)
            putString("password", pass)
            apply()
        }
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Registrasi Gagal")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
