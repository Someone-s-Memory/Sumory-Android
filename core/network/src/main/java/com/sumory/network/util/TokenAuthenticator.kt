package com.sumory.network.util

import com.sumory.datastore.auth.TokenDataStore
import com.sumory.network.api.AuthApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Provider

class TokenAuthenticator(
    private val tokenDataStore: TokenDataStore,
    private val authApiProvider: Provider<AuthApi>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refresh = runBlocking { tokenDataStore.refreshToken.firstOrNull() } ?: return null
        val refreshResponse = runBlocking {
            runCatching {
                authApiProvider.get().refresh("Bearer $refresh")
            }.getOrNull()
        } ?: return null

        runBlocking {
            tokenDataStore.saveTokens(refreshResponse.access, refreshResponse.refresh)
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer ${refreshResponse.access}")
            .build()
    }
}
