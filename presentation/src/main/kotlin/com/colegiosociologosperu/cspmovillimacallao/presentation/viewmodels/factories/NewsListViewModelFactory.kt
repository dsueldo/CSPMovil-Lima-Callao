package com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.NewsUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.NewsListViewModel
import javax.inject.Inject

class NewsListViewModelFactory @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val profileUseCase: ProfileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsListViewModel(newsUseCase, profileUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}