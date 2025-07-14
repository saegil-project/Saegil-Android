package com.saegil.data.remote

import com.saegil.data.model.NewsItemDto

interface NewsService {

    suspend fun getNewsByTopics(): List<NewsItemDto>

}