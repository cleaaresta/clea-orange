package com.example.clea_orange.News

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("items") val items: List<NewsItem>?
)

data class NewsItem(
    @SerializedName("title") val title: String?,
    @SerializedName("pubDate") val pubDate: String?,
    @SerializedName("link") val link: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("enclosure") val enclosure: NewsEnclosure?
)

data class NewsEnclosure(
    @SerializedName("link") val link: String?
)
