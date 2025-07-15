package com.saegil.data.di

import com.saegil.data.di.network.ConditionalAuthPlugin
import com.saegil.data.local.TokenDataSource
import com.saegil.data.remote.AssistantService
import com.saegil.data.remote.AssistantServiceImpl
import com.saegil.data.remote.FeedService
import com.saegil.data.remote.FeedServiceImpl
import com.saegil.data.remote.HttpRoutes.ASSISTANT
import com.saegil.data.remote.HttpRoutes.GET_REALTIME_TOKEN
import com.saegil.data.remote.HttpRoutes.NEWS
import com.saegil.data.remote.HttpRoutes.NEWS_INTERESTS
import com.saegil.data.remote.HttpRoutes.OAUTH_LOGOUT
import com.saegil.data.remote.HttpRoutes.OAUTH_VALIDATE_TOKEN
import com.saegil.data.remote.HttpRoutes.OAUTH_WITHDRAWAL
import com.saegil.data.remote.HttpRoutes.SIMULATION_LOG
import com.saegil.data.remote.HttpRoutes.TTS
import com.saegil.data.remote.HttpRoutes.USER
import com.saegil.data.remote.InterestService
import com.saegil.data.remote.InterestServiceImpl
import com.saegil.data.remote.MapService
import com.saegil.data.remote.MapServiceImpl
import com.saegil.data.remote.NewsService
import com.saegil.data.remote.NewsServiceImpl
import com.saegil.data.remote.OAuthService
import com.saegil.data.remote.OAuthServiceImpl
import com.saegil.data.remote.RealTimeService
import com.saegil.data.remote.RealTimeServiceImpl
import com.saegil.data.remote.QuizService
import com.saegil.data.remote.QuizServiceImpl
import com.saegil.data.remote.ScenarioService
import com.saegil.data.remote.ScenarioServiceImpl
import com.saegil.data.remote.SimulationLogService
import com.saegil.data.remote.SimulationLogServiceImpl
import com.saegil.data.remote.TextToSpeechService
import com.saegil.data.remote.TextToSpeechServiceImpl
import com.saegil.data.remote.UserInfoService
import com.saegil.data.remote.UserInfoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        tokenDataSource: TokenDataSource
    ): HttpClient {
        return HttpClient(CIO) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
            }
            install(ConditionalAuthPlugin) {
                tokenProvider = { tokenDataSource.getToken().accessToken }
                shouldAttach = { request ->
                    val path = request.url.toString()
                    setOf(
                        OAUTH_LOGOUT,
                        OAUTH_WITHDRAWAL,
                        OAUTH_VALIDATE_TOKEN,
                        SIMULATION_LOG,
                        TTS,
                        USER,
                        ASSISTANT,
                        GET_REALTIME_TOKEN,
                        NEWS_INTERESTS,
                        NEWS
                    ).any { it in path }
                }
            }
            install(io.ktor.client.plugins.websocket.WebSockets)
        }
    }

    @Provides
    @Singleton
    fun provideFeedService(client: HttpClient): FeedService {
        return FeedServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideMapService(client: HttpClient): MapService {
        return MapServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideOAuthService(client: HttpClient): OAuthService {
        return OAuthServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideScenarioService(client: HttpClient): ScenarioService {
        return ScenarioServiceImpl(client)
    }


    @Provides
    @Singleton
    fun provideAssistantService(client: HttpClient): AssistantService {
        return AssistantServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideSimulationLogService(client: HttpClient): SimulationLogService {
        return SimulationLogServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideTextToSpeechService(client: HttpClient): TextToSpeechService {
        return TextToSpeechServiceImpl(client)
    }
    
    @Provides
    @Singleton
    fun provideUserInfoService(client: HttpClient): UserInfoService {
        return UserInfoServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideInterestService(client: HttpClient): InterestService {
        return InterestServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideNewsService(client: HttpClient): NewsService {
        return NewsServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideQuizService(client: HttpClient): QuizService {
        return QuizServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideRealTimeService(client: HttpClient): RealTimeService {
        return RealTimeServiceImpl(client)
    }

}