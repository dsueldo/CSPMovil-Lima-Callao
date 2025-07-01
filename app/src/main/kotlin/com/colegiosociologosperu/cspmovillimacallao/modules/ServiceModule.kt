package com.colegiosociologosperu.cspmovillimacallao.modules

import com.colegiosociologosperu.cspmovillimacallao.data.repositories.AccountServiceImpl
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.BenefitsRepositoryImpl
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.NewsRepositoryImpl
import com.colegiosociologosperu.cspmovillimacallao.data.repositories.UserProfileServiceImpl
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.AuthRepository
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.BenefitsRepository
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.NewsRepository
import com.colegiosociologosperu.cspmovillimacallao.domain.repositories.UserProfileService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun provideAuthService(authServiceImpl: AccountServiceImpl): AuthRepository

    @Binds
    abstract fun bindNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindBenefitsRepository(benefitsRepositoryImpl: BenefitsRepositoryImpl): BenefitsRepository

    @Binds
    abstract fun provideUserProvideService(userProfileServiceImpl: UserProfileServiceImpl): UserProfileService
}