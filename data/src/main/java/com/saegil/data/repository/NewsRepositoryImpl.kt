package com.saegil.data.repository

import com.saegil.data.remote.NewsService
import com.saegil.domain.model.NewsItem
import com.saegil.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : NewsRepository {

//    override fun getNewsByTopics(topics: List<String>): List<NewsItem> {
//
//    }

}