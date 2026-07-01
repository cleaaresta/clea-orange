package com.example.clea_orange

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityRegistrationBinding
import com.example.clea_orange.utils.NotificationHelper
import com.example.clea_orange.utils.PermissionHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    // Launcher untuk meminta izin notifikasi (Android 13+)
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

        // 1. CEK IZIN SAAT HALAMAN DIBUKA (Sesuai Gambar 3)
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
                // Simpan data
                saveRegistrationData(nama, email, username, password)
                
                // 2. SIAPKAN INTENT UNTUK HALAMAN TUJUAN (Sesuai Gambar 4)
                val intent = Intent(this, SuccessActivity::class.java)
                
                // 3. TAMPILKAN NOTIFIKASI
                NotificationHelper.showNotification(
                    this,
                    "Pesanan Anda", // Judul sesuai materi
                    "Halo $nama, Pesanan Anda Sedang Diproses", // Pesan sesuai materi
                    intent
                )

                Toast.makeText(this, "Registrasi Berhasil! Silahkan cek notifikasi Anda.", Toast.LENGTH_LONG).show()

                // 4. MATIKAN startActivity AGAR BISA TES KLIK NOTIFIKASI (Sesuai Gambar 4)
                // startActivity(intent) 
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
