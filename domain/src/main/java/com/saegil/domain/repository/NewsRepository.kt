package com.saegil.domain.repository

import com.saegil.domain.model.NewsItem

interface NewsRepository {

    suspend fun getNewsByTopics(): List<NewsItem>

}