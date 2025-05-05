package com.saegil.data.remote

import io.ktor.client.HttpClient
import javax.inject.Inject

class MessageLogServiceImpl @Inject constructor(
    private val client: HttpClient
) : MessageLogService {
}