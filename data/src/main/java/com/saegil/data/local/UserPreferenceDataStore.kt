package com.saegil.data.local

import android.content.Context
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun savePreferredTopics(topics: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[DataStoreKeys.PREFERRED_TOPICS] = topics.toSet()
        }
    }

    fun getPreferredTopics(): Flow<List<String>> {
        return context.dataStore.data
            .map { prefs ->
                prefs[DataStoreKeys.PREFERRED_TOPICS]?.toList() ?: emptyList()
            }
    }
}

object DataStoreKeys {
    val PREFERRED_TOPICS = stringSetPreferencesKey("preferred_topics")
}