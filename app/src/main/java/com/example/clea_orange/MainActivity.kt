package com.example.clea_orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clea_orange.databinding.ActivityMainBinding
import com.example.clea_orange.pertemuan2.HitungActivity
import com.example.clea_orange.pertemuan4.Tampilan2Activity
import com.example.clea_orange.pertemuan4.Tampilan3Activity
import com.example.clea_orange.pertemuan6.WebViewActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menggunakan mainRoot sesuai ID di activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainRoot) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mengambil data username dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvWelcomeUser.text = getString(R.string.welcome_user, username)

        // Menu 1: Bangun Ruang
        binding.btnBangunRuang.setOnClickListener {
            pindahHalaman(HitungActivity::class.java, "Bangun Ruang", "Menghitung Luas dan Volume")
        }

        // Menu 2: Ekosistem
        binding.btnCustom1.setOnClickListener {
            pindahHalaman(Tampilan2Activity::class.java, "Ekosistem", "Harmoni antara alam dan ketenangan.")
        }

        // Menu 3: Inspirasi
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
            .setTitle(R.string.logout_confirm_title)
            .setMessage(R.string.logout_confirm_msg)
            .setPositiveButton(R.string.logout_yes) { _, _ ->
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
            .setNegativeButton(R.string.logout_no) { dialog, _ ->
                dialog.dismiss()
                Snackbar.make(binding.root, getString(R.string.logout_cancelled), Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }
}
