package com.example.clea_orange.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.clea_orange.AuthActivity
import com.example.clea_orange.Home.pertemuan2.HitungActivity
import com.example.clea_orange.Home.pertemuan4.Tampilan2Activity
import com.example.clea_orange.Home.pertemuan4.Tampilan3Activity
import com.example.clea_orange.Home.pertemuan4.SettingsActivity
import com.example.clea_orange.Home.pertemuan6.WebViewActivity
import com.example.clea_orange.R
import com.example.clea_orange.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Welcome Text dari SharedPreferences
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvWelcomeUser.text = "Halo, $username!"

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

        // Menu 4: Web Admin (WebView)
        binding.btnWeb.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            startActivity(intent)
        }

        // Tombol Settings (ListView - Privacy, About, dll)
        binding.btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        // Tombol Laporan Baru (MaterialButton)
        binding.btnQuickReport.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Laporan akan segera hadir!", Toast.LENGTH_SHORT).show()
        }

        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun pindahHalaman(tujuan: Class<*>, judul: String, desc: String) {
        val intent = Intent(requireContext(), tujuan)
        intent.putExtra("EXTRA_JUDUL", judul)
        intent.putExtra("EXTRA_DESC", desc)
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    clear()
                    apply()
                }
                val intent = Intent(requireContext(), AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
