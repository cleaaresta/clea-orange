package com.example.clea_orange.pertemuan4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.databinding.ActivityDashboardBinding
import com.example.clea_orange.pertemuan2.HitungActivity
import com.example.clea_orange.pertemuan3.LoginActivity
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol 1: Ke HitungActivity
        binding.btnBangunRuang.setOnClickListener {
            pindahHalaman(HitungActivity::class.java, "Rumus Bangun Ruang", "Halaman ini menghitung Luas dan Volume")
        }

        // Tombol 2: Ke Tampilan2Activity
        binding.btnCustom1.setOnClickListener {
            pindahHalaman(
                Tampilan2Activity::class.java,
                "Ekosistem Asri",
                "Lingkungan yang ideal adalah simfoni antara alam dan ketenangan. Di sini, udara segar mengalir di antara pepohonan rindang, menciptakan ruang bernapas bagi jiwa. Cahaya matahari yang menyusup di sela dedaunan memberikan energi positif, sementara kebersihan yang terjaga mencerminkan harmoni antara manusia dan bumi yang kita pijak."
            )
        }

        // Tombol 3: Ke Tampilan3Activity
        binding.btnCustom2.setOnClickListener {
            pindahHalaman(
                Tampilan3Activity::class.java,
                "Ruang Inspirasi",
                "Lingkungan yang sehat bukan sekadar tempat, melainkan sumber inspirasi. Ruang yang tertata rapi dengan sentuhan elemen hijau mampu meningkatkan kreativitas dan kejernihan berpikir. Dalam lingkungan yang mendukung, setiap sudut menjadi peluang untuk tumbuh, berkembang, dan menciptakan inovasi yang berdampak bagi masa depan yang lebih hijau."
            )
        }

        // Tombol 4: Logout dengan Konfirmasi
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun pindahHalaman(tujuan: Class<*>, judul: String, desc: String) {
        val intent = Intent(this, tujuan)
        intent.putExtra("EXTRA_JUDUL", judul)
        intent.putExtra("EXTRA_DESC", desc)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Iya") { _, _ ->
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            .setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}