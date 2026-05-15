package com.colegiosociologosperu.cspmovillimacallao.modules

import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.UserProfileService
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.BenefitsUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.NewsUseCase
import com.colegiosociologosperu.cspmovillimacallao.domain.usecases.ProfileUseCase
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.BenefitsListViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.benefits.detail.BenefitsDetailViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.editprofile.EditProfileViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.factories.BenefitsListViewModelFactory
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.factories.NewsListViewModelFactory
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.NewsListViewModel
import com.colegiosociologosperu.cspmovillimacallao.presentation.viewmodels.news.detail.NewsDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideProfileUseCase(repository: UserProfileService): ProfileUseCase = ProfileUseCase(repository)

    @Provides
    fun provideEditProfileViewModel(profileUseCase: ProfileUseCase,): EditProfileViewModel = EditProfileViewModel(profileUseCase)

    @Provides
    fun provideNewsListViewModel(newsUseCase: NewsUseCase, profileUseCase: ProfileUseCase): NewsListViewModel = NewsListViewModel(newsUseCase, profileUseCase)

    @Provides
    fun provideNewsDetailViewModel(useCase: NewsUseCase, profileUseCase: ProfileUseCase): NewsDetailViewModel = NewsDetailViewModel(useCase, profileUseCase)

    @Provides
    fun provideNewsListViewModelFactory(newsUseCase: NewsUseCase, profileUseCase: ProfileUseCase): NewsListViewModelFactory = NewsListViewModelFactory(newsUseCase, profileUseCase)

    @Provides
    fun provideBenefitsListViewModel(useCase: BenefitsUseCase): BenefitsListViewModel = BenefitsListViewModel(useCase)

    @Provides
    fun provideBenefitsDetailViewModel(useCase: BenefitsUseCase): BenefitsDetailViewModel = BenefitsDetailViewModel(useCase)

    @Provides
    fun provideBenefitsListViewModelFactory(useCase: BenefitsUseCase): BenefitsListViewModelFactory = BenefitsListViewModelFactory(useCase)
}