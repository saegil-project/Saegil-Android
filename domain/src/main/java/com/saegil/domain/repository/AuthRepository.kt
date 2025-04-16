package com.saegil.domain.repository

import android.content.Context

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
    suspend fun login(context: Context): Boolean
}