package com.saegil.data.di

import com.saegil.data.remote.FeedService
import com.saegil.data.remote.FeedServiceImpl
import com.saegil.data.remote.MapService
import com.saegil.data.remote.MapServiceImpl
import com.saegil.data.remote.OAuthService
import com.saegil.data.remote.OAuthServiceImpl
import com.saegil.data.remote.ScenarioService
import com.saegil.data.remote.ScenarioServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

import kotlinx.serialization.json.Json
import okhttp3.internal.connection.ConnectInterceptor.intercept
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
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
}