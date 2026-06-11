package com.example.clea_orange.News

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clea_orange.R
import com.example.clea_orange.databinding.ItemNewsBinding

class NewsAdapter(private var newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.binding.tvNewsTitle.text = news.title

        // Perbaikan: Ambil URL dari enclosure link, jika kosong baru gunakan thumbnail
        val imageUrl = if (!news.enclosure?.link.isNullOrEmpty()) {
            news.enclosure?.link
        } else {
            news.thumbnail
        }

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.bb) // Gambar saat loading
            .error(R.drawable.bb)       // Gambar jika URL rusak/gagal load
            .into(holder.binding.ivNewsImage)
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newList: List<NewsItem>) {
        newsList = newList
        notifyDataSetChanged()
    }
}