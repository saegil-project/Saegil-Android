package com.saegil.data.di

import com.saegil.data.BuildConfig
import com.saegil.data.remote.AssistantApi
import com.saegil.data.remote.FeedService
import com.saegil.data.remote.FeedServiceImpl
import com.saegil.data.remote.HttpRoutes.OAUTH_LOGOUT
import com.saegil.data.remote.HttpRoutes.OAUTH_VALIDATE_TOKEN
import com.saegil.data.remote.HttpRoutes.OAUTH_WITHDRAWAL
import com.saegil.data.remote.MapService
import com.saegil.data.remote.MapServiceImpl
import com.saegil.data.remote.OAuthService
import com.saegil.data.remote.OAuthServiceImpl
import com.saegil.data.remote.ScenarioService
import com.saegil.data.remote.ScenarioServiceImpl
import com.saegil.data.repository.AssistantRepositoryImpl
import com.saegil.domain.repository.AssistantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        tokenDataSource: TokenDataSource
    ): HttpClient {
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
            install(ConditionalAuthPlugin) {
                tokenProvider = { tokenDataSource.getToken().accessToken }
                shouldAttach = { request ->
                    val path = request.url.toString()
                    path in setOf(
                        OAUTH_LOGOUT,
                        OAUTH_WITHDRAWAL,
                        OAUTH_VALIDATE_TOKEN
                    )
                }
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

//    @Provides
//    @Singleton
//    fun provideAssistantService(client: HttpClient): AssistantService {
//        return AssistantServiceImpl(client)
//    }


    @Provides
    @Singleton
    fun provideAssistantRepository(api: AssistantApi): AssistantRepository =
        AssistantRepositoryImpl(api)

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // 로그 레벨 설정
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // 로깅 인터셉터 추가
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization-Header", "Bearer saegil-dev-test-token")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAssistantApi(retrofit: Retrofit): AssistantApi =
        retrofit.create(AssistantApi::class.java)
}