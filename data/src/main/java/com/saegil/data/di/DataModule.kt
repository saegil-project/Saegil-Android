package com.saegil.data.di

import android.content.Context
import com.saegil.data.local.ThreadPreferencesManager
import com.saegil.data.local.TokenDataSource
import com.saegil.data.local.TokenDataSourceImpl
import com.saegil.data.remote.AssistantService
import com.saegil.data.remote.FeedService
import com.saegil.data.remote.InterestService
import com.saegil.data.remote.MapService
import com.saegil.data.remote.NewsService
import com.saegil.data.remote.OAuthService
import com.saegil.data.remote.QuizService
import com.saegil.data.remote.ScenarioService
import com.saegil.data.remote.SimulationLogService
import com.saegil.data.remote.TextToSpeechService
import com.saegil.data.remote.UserInfoService
import com.saegil.data.repository.AssistantRepositoryImpl
import com.saegil.data.repository.FeedRepositoryImpl
import com.saegil.data.repository.MapRepositoryImpl
import com.saegil.data.repository.NewsRepositoryImpl
import com.saegil.data.repository.OAuthRepositoryImpl
import com.saegil.data.repository.QuizRepositoryImpl
import com.saegil.data.repository.ScenarioRepositoryImpl
import com.saegil.data.repository.SimulationLogRepositoryImpl
import com.saegil.data.repository.TextToSpeechRepositoryImpl
import com.saegil.data.repository.UserInfoRepositoryImpl
import com.saegil.data.repository.UserTopicRepositoryImpl
import com.saegil.domain.repository.AssistantRepository
import com.saegil.domain.repository.FeedRepository
import com.saegil.domain.repository.MapRepository
import com.saegil.domain.repository.NewsRepository
import com.saegil.domain.repository.OAuthRepository
import com.saegil.domain.repository.QuizRepository
import com.saegil.domain.repository.ScenarioRepository
import com.saegil.domain.repository.SimulationLogRepository
import com.saegil.domain.repository.TextToSpeechRepository
import com.saegil.domain.repository.UserInfoRepository
import com.saegil.domain.repository.UserTopicRepository
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
    fun provideTokenDataSource(@ApplicationContext context: Context): TokenDataSource {
        return TokenDataSourceImpl(context)
    }

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
    fun provideAssistantRepository(
        assistantService: AssistantService,
        threadPreferencesManager: ThreadPreferencesManager
    ): AssistantRepository {
        return AssistantRepositoryImpl(assistantService, threadPreferencesManager)
    }

    @Provides
    @Singleton
    fun provideTextToSpeechRepository(textToSpeechService: TextToSpeechService): TextToSpeechRepository {
        return TextToSpeechRepositoryImpl(textToSpeechService)
    }

    @Provides
    @Singleton
    fun provideSimulationLogRepository(simulationLogService: SimulationLogService): SimulationLogRepository {
        return SimulationLogRepositoryImpl(simulationLogService)
    }

    @Provides
    @Singleton
    fun provideUserInfoRepository(userInfoService: UserInfoService): UserInfoRepository {
        return UserInfoRepositoryImpl(userInfoService)
    }

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(interestService: InterestService): UserTopicRepository {
        return UserTopicRepositoryImpl(interestService)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(quizService: QuizService): QuizRepository {
        return QuizRepositoryImpl(quizService)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsService: NewsService): NewsRepository{
        return NewsRepositoryImpl(newsService)
    }


}