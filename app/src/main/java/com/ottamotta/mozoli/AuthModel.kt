package com.ottamotta.mozoli

import android.app.Activity
import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.ottamotta.mozoli.config.ServerConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import sample.R

class AuthModel(private val context: Context) {

    private val authManager: SecureCredentialsManager

    init {
        val auth0 = Auth0(context)
        val apiClient = AuthenticationAPIClient(auth0)
        authManager = SecureCredentialsManager(context, apiClient, SharedPreferencesStorage(context))
    }

    suspend fun getTokenIfLoggedIn(): Credentials {
        return awaitCallback { authManager.getCredentials(it) };
    }

    fun isLoggedIn() = authManager.hasValidCredentials()

    fun apiWrapper() =
        ApiWrapper(ServerConfig.SERVER_URL) {
            GlobalScope.async { if (isLoggedIn()) getTokenIfLoggedIn() else null } }

    fun authenticate(activity : Activity) {
        val account = Auth0(context.getString(R.string.com_auth0_client_id), context.getString(R.string.com_auth0_domain))
        account.isOIDCConformant = true

        GlobalScope.launch {
            try {
                val credentials = awaitCallback<Credentials> {
                    WebAuthProvider.init(account)
                        .withAudience("https://${context.getString(R.string.com_auth0_domain)}/userinfo")
                        .start(activity, it)
                }
                authManager.saveCredentials(credentials)
            } catch(e : Exception) {
                if (e.hasDialog()) {
                    (e as ExceptionWithDialog).dialog.show()
                } else {
                    Log.e("AuthModel", "Auth expection: ${e.message}")
                }
            }



        }
    }

}