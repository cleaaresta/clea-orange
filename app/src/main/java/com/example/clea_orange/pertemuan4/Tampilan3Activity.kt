package com.example.clea_orange.pertemuan4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityTampilan3Binding

class Tampilan3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityTampilan3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTampilan3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sembunyikan ActionBar bawaan
        supportActionBar?.hide()

        val judul = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsi = intent.getStringExtra("EXTRA_DESC")

        binding.tvJudulHalaman.text = judul
        binding.tvDescHalaman.text = deskripsi

        // LOGIKA TOMBOL KEMBALI
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}