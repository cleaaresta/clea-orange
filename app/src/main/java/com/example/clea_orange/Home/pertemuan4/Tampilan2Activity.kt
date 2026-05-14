package com.example.clea_orange.Home.pertemuan4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityTampilan2Binding

class Tampilan2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityTampilan2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilan2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sembunyikan ActionBar bawaan
        supportActionBar?.hide()

        // Ambil data dari Intent
        val judul = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsi = intent.getStringExtra("EXTRA_DESC")

        binding.tvJudulHalaman.text = judul
        binding.tvDescHalaman.text = deskripsi

        // LOGIKA TOMBOL KEMBALI
        binding.btnBack.setOnClickListener {
            finish() // Menutup halaman ini dan kembali
        }
    }
}