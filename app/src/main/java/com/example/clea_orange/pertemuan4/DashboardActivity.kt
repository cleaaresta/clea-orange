package com.example.clea_orange.pertemuan4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.clea_orange.AuthActivity
import com.example.clea_orange.databinding.ActivityDashboardBinding
import com.example.clea_orange.pertemuan2.HitungActivity
import com.example.clea_orange.pertemuan6.WebViewActivity
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data username dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvWelcomeUser.text = "Halo, $username!"

        // Menu 1: Bangun Ruang
        binding.btnBangunRuang.setOnClickListener {
            pindahHalaman(HitungActivity::class.java, "Bangun Ruang", "Menghitung Luas dan Volume")
        }

        // Menu 2: Ekosistem (Pastikan Tampilan2Activity ada di package yang sama)
        binding.btnCustom1.setOnClickListener {
            pindahHalaman(Tampilan2Activity::class.java, "Ekosistem", "Harmoni antara alam dan ketenangan.")
        }

        // Menu 3: Inspirasi (Pastikan Tampilan3Activity ada di package yang sama)
        binding.btnCustom2.setOnClickListener {
            pindahHalaman(Tampilan3Activity::class.java, "Inspirasi", "Sumber kreativitas bagi masa depan.")
        }

        // Menu 4: Web Bina Desa (WebView)
        binding.btnWeb.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        // Logout
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
            .setPositiveButton("Ya") { _, _ ->
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    clear()
                    apply()
                }
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}