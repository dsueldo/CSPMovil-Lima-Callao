package com.colegiosociologosperu.cspmovillimacallao.domain.repositories

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News

interface NewsRepository {
    suspend fun getAllNews(): List<News>
    suspend fun getNewsDetail(newsId: String): News
    suspend fun toggleFavorite(newsId: String)
    suspend fun isFavorite(newsId: String): Boolean
    suspend fun getFavoriteNews(): List<News>
}
