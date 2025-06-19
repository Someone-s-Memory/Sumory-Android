package com.sumory.data.datastore.auth

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private const val TOKEN_PREFERENCES = "token_prefs"
val Context.tokenDataStore by preferencesDataStore(TOKEN_PREFERENCES)

object TokenKeys {
    val ACCESS = stringPreferencesKey("access_token")
    val REFRESH = stringPreferencesKey("refresh_token")
}

class TokenDataStore(private val context: Context) {
    val accessToken = context.tokenDataStore.data.map { it[TokenKeys.ACCESS] }
    val refreshToken = context.tokenDataStore.data.map { it[TokenKeys.REFRESH] }

    suspend fun saveTokens(access: String, refresh: String) {
        context.tokenDataStore.edit {
            it[TokenKeys.ACCESS] = access
            it[TokenKeys.REFRESH] = refresh
        }
    }

    suspend fun clearTokens() {
        context.tokenDataStore.edit { it.clear() }
    }
}