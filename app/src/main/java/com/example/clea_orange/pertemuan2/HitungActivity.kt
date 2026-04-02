package com.example.clea_orange.pertemuan2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.R

class HitungActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Kode enableEdgeToEdge() dan Insets dihapus agar padding XML bisa berfungsi
        setContentView(R.layout.activity_hitung)

        // 1. Kenalkan komponen dari XML ke Kotlin
        val etAlas = findViewById<EditText>(R.id.etAlas)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val btnHitungLuas = findViewById<Button>(R.id.btnHitungLuas)
        val tvHasilLuas = findViewById<TextView>(R.id.tvHasilLuas)

        val etSisi = findViewById<EditText>(R.id.etSisi)
        val btnHitungVolume = findViewById<Button>(R.id.btnHitungVolume)
        val tvHasilVolume = findViewById<TextView>(R.id.tvHasilVolume)

        // 2. Logika ketika Tombol Hitung Luas (Segitiga) di-klik
        btnHitungLuas.setOnClickListener {
            val teksAlas = etAlas.text.toString()
            val teksTinggi = etTinggi.text.toString()

            if (teksAlas.isNotEmpty() && teksTinggi.isNotEmpty()) {
                val alas = teksAlas.toDouble()
                val tinggi = teksTinggi.toDouble()
                val luas = 0.5 * alas * tinggi

                // Tampilkan di layar
                tvHasilLuas.text = "Hasil Luas: $luas"

                // Tampilkan di Logcat
                Log.d("HitungActivity", "Berhasil menghitung luas segitiga: $luas")
            } else {
                Toast.makeText(this, "Alas dan Tinggi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Logika ketika Tombol Hitung Volume (Kubus) di-klik
        btnHitungVolume.setOnClickListener {
            val teksSisi = etSisi.text.toString()

            if (teksSisi.isNotEmpty()) {
                val sisi = teksSisi.toDouble()
                val volume = sisi * sisi * sisi

                // Tampilkan di layar
                tvHasilVolume.text = "Hasil Volume: $volume"

                // Tampilkan di Logcat
                Log.d("HitungActivity", "Berhasil menghitung volume kubus: $volume")
            } else {
                Toast.makeText(this, "Sisi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}