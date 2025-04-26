package com.saegil.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.saegil.data.model.TokenDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "tokens")

class TokenDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenDataSource {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveToken(tokenDto: TokenDto) {
        context.tokenDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = tokenDto.accessToken
            preferences[REFRESH_TOKEN_KEY] = tokenDto.refreshToken
        }
    }

    override suspend fun getToken(): TokenDto {
        return TokenDto(
            accessToken = context.tokenDataStore.data.first()[ACCESS_TOKEN_KEY] ?: "",
            refreshToken = context.tokenDataStore.data.first()[REFRESH_TOKEN_KEY] ?: ""
        )
    }

    override suspend fun clearTokens() {
        context.tokenDataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }
}