package com.saegil.data.di

import com.saegil.data.local.TokenDao
import com.saegil.data.remote.FeedService
import com.saegil.data.remote.MapService
import com.saegil.data.remote.OAuthService
import com.saegil.data.repository.FeedRepositoryImpl
import com.saegil.data.repository.MapRepositoryImpl
import com.saegil.data.repository.OAuthRepositoryImpl
import com.saegil.domain.repository.FeedRepository
import com.saegil.domain.repository.MapRepository
import com.saegil.domain.repository.OAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFeedRepository(feedService: FeedService): FeedRepository {
        return FeedRepositoryImpl(feedService)
    }

    @Provides
    @Singleton
    fun provideMapRepository(mapService: MapService): MapRepository {
        return MapRepositoryImpl(mapService)
    }

    @Provides
    @Singleton
    fun provideOAuthRepository(
        oAuthService: OAuthService,
        tokenDao: TokenDao
    ): OAuthRepository {
        return OAuthRepositoryImpl(oAuthService, tokenDao)
    }

}