package com.sumory.network.util

import android.util.Log
import com.sumory.datastore.auth.TokenDataStore
import com.sumory.network.api.AuthApi
import com.sumory.network.di.AuthApiProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    @AuthApiProvider private val authApiProvider: Provider<AuthApi>
) : Authenticator {

    // 메모리 내 재시도 방지용
    @Volatile
    private var hasRefreshFailed = false

    override fun authenticate(route: Route?, response: Response): Request? {
        if (hasRefreshFailed) {
            Log.w("TokenAuthenticator", "Previous refresh failed. Skipping further attempts.")
            return null
        }

        val token = runBlocking { tokenDataStore.accessToken.firstOrNull() }
        Log.d("TokenAuthenticator", "authenticate: current accessToken = ${token?.mask()}")

        synchronized(this) {
            val newToken = runBlocking { tokenDataStore.accessToken.firstOrNull() }

            if (token != newToken) {
                Log.d("TokenAuthenticator", "authenticate: token was already refreshed in another thread")
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            }

            val refreshToken = runBlocking { tokenDataStore.refreshToken.firstOrNull() }
            if (refreshToken == null) {
                Log.w("TokenAuthenticator", "authenticate: refreshToken is null, clearing tokens")
                runBlocking { tokenDataStore.clearTokens() }
                return null
            }

            Log.d("TokenAuthenticator", "authenticate: attempting token refresh with refreshToken = ${refreshToken.mask()}")

            val refreshResponse = runBlocking {
                runCatching {
                    tokenDataStore.clearAccessToken()

                    val authApi = authApiProvider.get()
                    authApi.refresh("Bearer $refreshToken")
                }.onSuccess {
                    Log.d("TokenAuthenticator", "authenticate: token refreshed successfully, new accessToken = ${it.access.mask()}")
                }.onFailure {
                    Log.e("TokenAuthenticator", "authenticate: token refresh failed", it)
                    hasRefreshFailed = true
                    tokenDataStore.clearTokens()
                }.getOrNull()
            } ?: return null

            runBlocking {
                tokenDataStore.saveTokens(refreshResponse.access, refreshResponse.refresh)
            }
            Log.d("TokenAuthenticator", "authenticate: new tokens saved successfully")

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${refreshResponse.access}")
                .build()
        }
    }

    // 확장 함수: 토큰 마스킹
    private fun String.mask(): String {
        return if (length > 10) take(5) + "..." + takeLast(5) else "****"
    }
}
