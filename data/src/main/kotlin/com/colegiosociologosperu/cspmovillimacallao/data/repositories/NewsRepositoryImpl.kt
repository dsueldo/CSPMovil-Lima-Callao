package com.colegiosociologosperu.cspmovillimacallao.data.repositories

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NewsRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("news_prefs", Context.MODE_PRIVATE)

    override suspend fun getAllNews(): List<News> {
        return try {
            firestore.collection("news")
                .orderBy("order", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(News::class.java)?.apply {
                        id = document.id
                    Log.d(TAG, "Current data image: $image")
                    Log.d(TAG, "Current data title: $title")
                    Log.d(TAG, "Current data content: $content")
                    Log.d(TAG, "Current data description: $description")
                    Log.d(TAG, "Current data source: $source")
                    Log.d(TAG, "Current data date: $date")
                }
            }
        } catch (e: Exception) {
            println("Error fetching all news: ${e.localizedMessage}")
            emptyList()
        }
    }

    override suspend fun getNewsDetail(newsId: String): News {
        return try {
            val document = firestore.collection("news")
                .document(newsId)
                .get()
                .await()
            
            document.toObject(News::class.java)?.apply {
                id = document.id
            } ?: throw Exception("News not found")
        } catch (e: Exception) {
            println("Error fetching news detail for $newsId: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun toggleFavorite(newsId: String) {
        val favorites = sharedPreferences.getStringSet("favorites", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (favorites.contains(newsId)) {
            favorites.remove(newsId)
        } else {
            favorites.add(newsId)
        }
        sharedPreferences.edit().putStringSet("favorites", favorites).apply()
    }

    override suspend fun isFavorite(newsId: String): Boolean {
        val favorites = sharedPreferences.getStringSet("favorites", emptySet())
        return favorites?.contains(newsId) == true
    }

    override suspend fun getFavoriteNews(): List<News> {
        val favoriteIds = sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
        if (favoriteIds.isEmpty()) return emptyList()
        
        val allNews = getAllNews()
        return allNews.filter { favoriteIds.contains(it.id) }
    }

    override suspend fun addNews(news: News) {
        try {
            // Find the current maximum order
            val querySnapshot = firestore.collection("news")
                .orderBy("order", Query.Direction.DESCENDING)
                .limit(10) // Check first few to skip outliers if necessary
                .get()
                .await()

            // Filter out the outlier order if it exists (e.g. the 7066... one)
            val lastNews = querySnapshot.documents.firstOrNull { 
                val order = it.getLong("order") ?: 0
                order < 1000000 // Reasonable threshold for normal order
            }
            
            val nextOrder = ((lastNews?.getLong("order") ?: 0L) + 1).toInt()
            val nextId = "news${numberToWords(nextOrder)}"

            val newsMap = hashMapOf(
                "image" to news.image,
                "title" to news.title,
                "content" to news.content,
                "description" to news.description,
                "order" to nextOrder,
                "source" to news.source,
                "date" to news.date,
                "id" to nextId
            )
            firestore.collection("news").document(nextId).set(newsMap).await()
        } catch (e: Exception) {
            println("Error adding news: ${e.localizedMessage}")
            throw e
        }
    }

    private fun numberToWords(n: Int): String {
        val units = arrayOf(
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        )
        val tens = arrayOf(
            "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
        )

        return when {
            n == 0 -> "Zero"
            n < 20 -> units[n]
            n < 100 -> tens[n / 10] + units[n % 10]
            else -> n.toString() // Fallback
        }
    }

    override suspend fun updateNews(news: News) {
        if (news.id.isEmpty()) {
            println("Error: Intentando actualizar noticia con ID vacío")
            throw Exception("Invalid document reference. The news ID is empty.")
        }
        try {
            val newsMap = hashMapOf(
                "image" to news.image,
                "title" to news.title,
                "content" to news.content,
                "description" to news.description,
                "order" to news.order,
                "source" to news.source,
                "date" to news.date,
                "id" to news.id
            )
            firestore.collection("news").document(news.id).update(newsMap as Map<String, Any>).await()
        } catch (e: Exception) {
            println("Error updating news ${news.id}: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun deleteNews(newsId: String) {
        try {
            firestore.collection("news").document(newsId).delete().await()
        } catch (e: Exception) {
            println("Error deleting news $newsId: ${e.localizedMessage}")
            throw e
        }
    }
}