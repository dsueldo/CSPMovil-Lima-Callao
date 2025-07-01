package com.colegiosociologosperu.cspmovillimacallao.modules

import android.content.Context
import com.colegiosociologosperu.cspmovillimacallao.data.cache.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
}