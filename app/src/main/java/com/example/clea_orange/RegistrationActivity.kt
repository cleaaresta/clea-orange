package com.example.clea_orange

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityRegistrationBinding
import com.example.clea_orange.utils.PermissionHelper
import com.example.clea_orange.utils.ReminderHelper // Import Helper Baru
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar // Import Calendar

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Izin Notifikasi Diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Izin Notifikasi Ditolak", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(this, permission)) {
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
        }

        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        binding.etRegEmail.setText(email)

        binding.btnDoRegister.setOnClickListener {
            val nama = binding.etRegNama.text.toString().trim()
            val username = binding.etRegUsername.text.toString().trim()
            val password = binding.etRegPassword.text.toString().trim()

            if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showErrorDialog("Semua field wajib diisi")
            } else {
                saveRegistrationData(nama, email, username, password)

                // --- IMPLEMENTASI REMINDER (Sesuai Contoh) ---

                // 1. Tentukan waktu (Contoh: 1 menit dari sekarang)
                val calendar = Calendar.getInstance().apply {
                    add(Calendar.MINUTE, 1)
                }

                // 2. Panggil ReminderHelper
                ReminderHelper.setReminder(
                    context = this,
                    hour = calendar.get(Calendar.HOUR_OF_DAY),
                    minute = calendar.get(Calendar.MINUTE),
                    title = "Reminder 1 Menit",
                    message = "Halo $nama, reminder ini muncul 1 menit setelah registrasi",
                    targetActivity = SuccessActivity::class.java
                )

                Toast.makeText(this, "Silahkan tunggu 1 Menit untuk menerima Notifikasi", Toast.LENGTH_LONG).show()

                // Opsional: Jika ingin pindah halaman langsung, aktifkan ini:
                // startActivity(Intent(this, SuccessActivity::class.java))
                // finish()
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