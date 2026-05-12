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
            firestore.collection("news")
                .document(newsId)
                .get()
                .await()
                .toObject(News::class.java) ?: throw Exception("News not found")
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
}
