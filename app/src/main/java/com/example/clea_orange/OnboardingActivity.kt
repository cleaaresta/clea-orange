package com.example.clea_orange

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.clea_orange.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingItems = listOf(
            OnboardingItem(
                "Selamat Datang",
                "Posyandu Digital membantu memantau kesehatan ibu dan anak dengan lebih mudah dan efisien.",
                R.drawable.bb
            ),
            OnboardingItem(
                "Pantau Perkembangan",
                "Catat dan pantau grafik pertumbuhan anak Anda secara rutin untuk memastikan tumbuh kembang yang optimal.",
                R.drawable.i
            ),
            OnboardingItem(
                "Layanan Terintegrasi",
                "Akses informasi kesehatan dan jadwal posyandu dalam satu genggaman.",
                R.drawable.j
            )
        )

        binding.viewPager.adapter = OnboardingAdapter(onboardingItems)
        
        // Menghubungkan dotsIndicator dengan ViewPager2
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnNext.text = if (position == onboardingItems.size - 1) "Ayo Mulai" else "Selanjutnya"
            }
        })

        binding.btnNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem + 1 < onboardingItems.size) {
                binding.viewPager.currentItem = currentItem + 1
            } else {
                navigateToLogin()
            }
        }

        binding.tvSkip.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        getSharedPreferences("user_pref", Context.MODE_PRIVATE).edit {
            putBoolean("firstRun", false)
        }
        
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }

    data class OnboardingItem(val title: String, val description: String, val imageRes: Int)

    inner class OnboardingAdapter(private val items: List<OnboardingItem>) :
        RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_onboarding, parent, false)
            return OnboardingViewHolder(view)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val ivImage = view.findViewById<ImageView>(R.id.ivOnboardingImage)
            private val tvTitle = view.findViewById<TextView>(R.id.tvOnboardingTitle)
            private val tvDesc = view.findViewById<TextView>(R.id.tvOnboardingDesc)

            fun bind(item: OnboardingItem) {
                ivImage.setImageResource(item.imageRes)
                tvTitle.text = item.title
                tvDesc.text = item.description
            }
        }
    }
}
