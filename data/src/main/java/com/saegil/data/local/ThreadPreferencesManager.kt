package com.saegil.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = ThreadPreferences.PREFERENCES_NAME
)

@Singleton
class ThreadPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val threadIdKey = stringPreferencesKey(ThreadPreferences.KEY_THREAD_ID)

    val threadId: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[threadIdKey]
        }

    suspend fun saveThreadId(threadId: String) {
        context.dataStore.edit { preferences ->
            preferences[threadIdKey] = threadId
        }
    }

    suspend fun clearThreadId() {
        context.dataStore.edit { preferences ->
            preferences.remove(threadIdKey)
        }
    }
} 