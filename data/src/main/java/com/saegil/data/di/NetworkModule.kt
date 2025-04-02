package com.saegil.data.di

import com.saegil.data.remote.FeedService
import com.saegil.data.remote.FeedServiceImpl
import com.saegil.data.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
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
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
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
    fun provideFeedRepository(feedService: FeedService): FeedRepository {
        return FeedRepository(feedService)
    }

}