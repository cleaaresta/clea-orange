package com.example.clea_orange.pertemuan2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityHitungBinding // Pastikan import ini sesuai

class HitungActivity : AppCompatActivity() {

    // 1. Deklarasi View Binding
    private lateinit var binding: ActivityHitungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inisialisasi View Binding
        binding = ActivityHitungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Logika Tombol Kembali
        binding.btnBack.setOnClickListener {
            finish() // Menutup activity saat ini dan kembali ke halaman sebelumnya
        }

        // 4. Logika ketika Tombol Hitung Luas (Segitiga) di-klik
        binding.btnHitungLuas.setOnClickListener {
            val teksAlas = binding.etAlas.text.toString()
            val teksTinggi = binding.etTinggi.text.toString()

            if (teksAlas.isNotEmpty() && teksTinggi.isNotEmpty()) {
                val alas = teksAlas.toDouble()
                val tinggi = teksTinggi.toDouble()
                val luas = 0.5 * alas * tinggi

                // Tampilkan di layar menggunakan binding
                binding.tvHasilLuas.text = "Hasil Luas: $luas"

                // Tampilkan di Logcat
                Log.d("HitungActivity", "Berhasil menghitung luas segitiga: $luas")
            } else {
                Toast.makeText(this, "Alas dan Tinggi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        // 5. Logika ketika Tombol Hitung Volume (Kubus) di-klik
        binding.btnHitungVolume.setOnClickListener {
            val teksSisi = binding.etSisi.text.toString()

            if (teksSisi.isNotEmpty()) {
                val sisi = teksSisi.toDouble()
                val volume = sisi * sisi * sisi

                // Tampilkan di layar menggunakan binding
                binding.tvHasilVolume.text = "Hasil Volume: $volume"

                // Tampilkan di Logcat
                Log.d("HitungActivity", "Berhasil menghitung volume kubus: $volume")
            } else {
                Toast.makeText(this, "Sisi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}