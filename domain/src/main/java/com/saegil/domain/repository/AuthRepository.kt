package com.saegil.domain.repository

interface AuthRepository {
    suspend fun isLoggedIn(): Boolean
}