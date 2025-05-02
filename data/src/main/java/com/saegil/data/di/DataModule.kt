package com.saegil.data.di

import android.content.Context
import com.saegil.data.local.TokenDataSource
import com.saegil.data.local.TokenDataSourceImpl
import com.saegil.data.remote.FeedService
import com.saegil.data.remote.MapService
import com.saegil.data.remote.OAuthService
import com.saegil.data.remote.ScenarioLogService
import com.saegil.data.remote.ScenarioService
import com.saegil.data.repository.FeedRepositoryImpl
import com.saegil.data.repository.MapRepositoryImpl
import com.saegil.data.repository.OAuthRepositoryImpl
import com.saegil.data.repository.ScenarioLogRepositoryImpl
import com.saegil.data.repository.ScenarioRepositoryImpl
import com.saegil.domain.repository.FeedRepository
import com.saegil.domain.repository.MapRepository
import com.saegil.domain.repository.OAuthRepository
import com.saegil.domain.repository.ScenarioLogRepository
import com.saegil.domain.repository.ScenarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideTokenDataSource(@ApplicationContext context: Context): TokenDataSource {
        return TokenDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideOAuthRepository(
        oAuthService: OAuthService,
        tokenDataSource: TokenDataSource
    ): OAuthRepository {
        return OAuthRepositoryImpl(oAuthService, tokenDataSource)
    }


    @Provides
    @Singleton
    fun provideScenarioRepository(scenarioService: ScenarioService): ScenarioRepository {
        return ScenarioRepositoryImpl(scenarioService)
    }

    @Provides
    @Singleton
    fun providesScenarioLogRepository(scenarioLogService: ScenarioLogService): ScenarioLogRepository {
        return ScenarioLogRepositoryImpl(scenarioLogService)
    }
}