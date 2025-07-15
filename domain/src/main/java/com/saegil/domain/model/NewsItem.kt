package com.saegil.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val id : Long,
    val title: String,
    val category: String,
    val thumbnailUrl: String,
    val date: String,
) : Parcelable
