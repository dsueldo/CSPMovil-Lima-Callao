package com.colegiosociologosperu.cspmovillimacallao.domain.usecases

import com.colegiosociologosperu.cspmovillimacallao.domain.entities.news.News
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.NewsRepository
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun getAllNews(): List<News> = repository.getAllNews()
    
    suspend fun getNewsDetail(newsId: String): News = repository.getNewsDetail(newsId)
    
    suspend fun toggleFavorite(newsId: String) = repository.toggleFavorite(newsId)
    
    suspend fun isFavorite(newsId: String): Boolean = repository.isFavorite(newsId)
    
    suspend fun getFavoriteNews(): List<News> = repository.getFavoriteNews()

    suspend fun addNews(news: News) = repository.addNews(news)

    suspend fun updateNews(news: News) = repository.updateNews(news)

    suspend fun deleteNews(newsId: String) = repository.deleteNews(newsId)
}
