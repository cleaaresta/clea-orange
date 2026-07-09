package com.example.clea_orange.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clea_orange.AuthActivity
import com.example.clea_orange.Home.pertemuan2.HitungActivity
import com.example.clea_orange.Home.pertemuan4.Tampilan2Activity
import com.example.clea_orange.Home.pertemuan4.Tampilan3Activity
import com.example.clea_orange.Home.pertemuan4.SettingsActivity
import com.example.clea_orange.Home.pertemuan6.WebViewActivity
import com.example.clea_orange.Home.pertemuan_10.TenthActivity
import com.example.clea_orange.Home.pertemuan_13.ThirteenthActivity
import com.example.clea_orange.News.NewsAdapter
import com.example.clea_orange.News.NewsApiService
import com.example.clea_orange.News.NewsResponse
import com.example.clea_orange.R
import com.example.clea_orange.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWelcomeText()
        setupMenuButtons()
        setupRecyclerView()
        fetchNewsData()

        binding.chipGroupHome.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chipId = checkedIds[0]
                val selectedChip = view.findViewById<Chip>(chipId)
                Snackbar.make(view, "Kategori: ${selectedChip.text}", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener { showLogoutDialog() }
    }

    private fun setupWelcomeText() {
        val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.tvWelcomeUser.text = "Halo, $username!"
    }

    private fun setupMenuButtons() {
        binding.btnBangunRuang.setOnClickListener {
            pindahHalaman(HitungActivity::class.java, "Bangun Ruang", "Menghitung Luas dan Volume")
        }
        binding.btnCustom1.setOnClickListener {
            pindahHalaman(Tampilan2Activity::class.java, "Ekosistem", "Harmoni antara alam dan ketenangan.")
        }
        binding.btnCustom2.setOnClickListener {
            pindahHalaman(Tampilan3Activity::class.java, "Inspirasi", "Sumber kreativitas bagi masa depan.")
        }
        binding.btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }
        binding.btnPertemuan10.setOnClickListener {
            startActivity(Intent(requireContext(), TenthActivity::class.java))
        }
        binding.btnPertemuan13.setOnClickListener {
            startActivity(Intent(requireContext(), ThirteenthActivity::class.java))
        }
        binding.btnQuickReport.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur Laporan akan segera hadir!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(mutableListOf())
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun fetchNewsData() {
        binding.pbNews.visibility = View.VISIBLE
        NewsApiService.create().getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (!isAdded) return
                binding.pbNews.visibility = View.GONE
                
                if (response.isSuccessful) {
                    val posts = response.body()?.items
                    if (!posts.isNullOrEmpty()) {
                        Log.d("BeritaAPI", "Data Berhasil Dimuat: ${posts.size} item")
                        newsAdapter.updateData(posts)
                    } else {
                        Log.e("BeritaAPI", "Data Berita Kosong")
                        Toast.makeText(requireContext(), "Tidak ada berita tersedia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("BeritaAPI", "Error HTTP: ${response.code()}")
                    Toast.makeText(requireContext(), "Gagal memuat berita: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                if (!isAdded) return
                binding.pbNews.visibility = View.GONE
                Log.e("BeritaAPI", "Failure: ${t.message}")
                Toast.makeText(requireContext(), "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun pindahHalaman(tujuan: Class<*>, judul: String, desc: String) {
        val intent = Intent(requireContext(), tujuan).apply {
            putExtra("EXTRA_JUDUL", judul)
            putExtra("EXTRA_DESC", desc)
        }
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE).edit().clear().apply()
                val intent = Intent(requireContext(), AuthActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
