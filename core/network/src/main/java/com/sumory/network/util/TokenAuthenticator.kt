//package com.sumory.network.util
//
//import com.sumory.data.datastore.auth.TokenDataStore
//import com.sumory.network.api.AuthApi
//import kotlinx.coroutines.flow.firstOrNull
//import kotlinx.coroutines.runBlocking
//import okhttp3.Authenticator
//import okhttp3.Request
//import okhttp3.Response
//import okhttp3.Route
//
//class TokenAuthenticator(
//    private val tokenDataStore: TokenDataStore,
//    private val authApi: AuthApi
//) : Authenticator {
//    override fun authenticate(route: Route?, response: Response): Request? {
//        val refresh = runBlocking { tokenDataStore.refreshToken.firstOrNull() } ?: return null
//        val refreshResponse = runBlocking {
//            runCatching { authApi.refresh(refresh) }.getOrNull()
//        } ?: return null
//
//        tokenDataStore.saveTokens(refreshResponse.access, refresh)
//
//        return response.request.newBuilder()
//            .header("Authorization", "Bearer ${refreshResponse.access}")
//            .build()
//    }
//}
