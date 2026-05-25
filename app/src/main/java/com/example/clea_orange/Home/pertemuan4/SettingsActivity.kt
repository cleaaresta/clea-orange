package com.example.clea_orange.Home.pertemuan4

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data untuk ListView
        val settingsOptions = arrayOf(
            "Profil Pengguna",
            "Notifikasi",
            "Privasi & Keamanan",
            "Bantuan & Dukungan",
            "Tentang Aplikasi",
            "Kebijakan Layanan"
        )

        // Menggunakan ArrayAdapter sederhana
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            settingsOptions
        )

        binding.lvSettings.adapter = adapter

        // Listener untuk item klik
        binding.lvSettings.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = settingsOptions[position]
            Toast.makeText(this, "Membuka: $selectedItem", Toast.LENGTH_SHORT).show()
        }

        // Set up Toolbar
        setSupportActionBar(binding.toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarSettings.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}