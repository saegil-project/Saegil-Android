package com.saegil.data.di.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.HttpHeaders
import io.ktor.util.AttributeKey

class ConditionalAuthPlugin(
    private val tokenProvider: suspend () -> String,
    private val shouldAttach: (HttpRequestBuilder) -> Boolean
) {

    class Config {
        lateinit var tokenProvider: suspend () -> String
        var shouldAttach: (HttpRequestBuilder) -> Boolean = { false }

        fun build() = ConditionalAuthPlugin(tokenProvider, shouldAttach)
    }

    companion object : HttpClientPlugin<Config, ConditionalAuthPlugin> {
        override val key = AttributeKey<ConditionalAuthPlugin>("ConditionalAuthPlugin")

        override fun prepare(block: Config.() -> Unit): ConditionalAuthPlugin {
            return Config().apply(block).build()
        }

        override fun install(plugin: ConditionalAuthPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                if (plugin.shouldAttach(context)) { // 이 context가 install에서의 request
                    val token = plugin.tokenProvider()
                    context.headers.append(HttpHeaders.Authorization, token)
                }
                proceed()
            }
        }
    }
}
