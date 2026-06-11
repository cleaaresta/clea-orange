package com.example.clea_orange.News

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v1/api.json")
    fun getNews(
        @Query("rss_url") rssUrl: String = "https://www.cnnindonesia.com/nasional/rss"
    ): Call<NewsResponse>

    companion object {
        private const val BASE_URL = "https://api.rss2json.com/"

        fun create(): NewsApiService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(NewsApiService::class.java)
        }
    }
}
