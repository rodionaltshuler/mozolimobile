package com.ottamotta.mozoli

import android.app.Activity
import android.content.Context
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.ottamotta.mozoli.config.ServerConfig
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import sample.R

class AuthModel(private val context: Context) {

    private val TAG = AuthModel::class.java.simpleName

    private val authManager: CredentialsManager

    init {
        val auth0 = Auth0(context)
        val apiClient = AuthenticationAPIClient(auth0)
        authManager = CredentialsManager(apiClient, SharedPreferencesStorage(context))
    }

    suspend fun getTokenIfLoggedIn(): Credentials {
        return awaitCallback { authManager.getCredentials(it) };
    }

    fun isLoggedIn() = authManager.hasValidCredentials()

    fun apiWrapper() =
        ApiWrapper(ServerConfig.SERVER_URL) {
            GlobalScope.async { if (isLoggedIn()) getTokenIfLoggedIn() else null } }

    fun authenticate(activity : Activity, actionAfterAuth: (() -> Unit)? = {}) {

        val account = Auth0(context.getString(R.string.com_auth0_client_id), context.getString(R.string.com_auth0_domain))
        account.isOIDCConformant = true

        GlobalScope.launch {
            try {
                if (!isLoggedIn()) {
                    val credentials = awaitCallback<Credentials> {
                        WebAuthProvider.init(account)
                            .withAudience("https://${context.getString(R.string.com_auth0_domain)}/userinfo")
                            .start(activity, it)
                    }
                    Log.i(TAG, "Saving credentials " + credentials.accessToken)
                    authManager.saveCredentials(credentials)
                }
                actionAfterAuth?.invoke()
            } catch(e : Exception) {
                if (e.hasDialog()) {
                    (e as ExceptionWithDialog).dialog.show()
                } else {
                    Log.e(TAG, "Auth expection: ${e.message}")
                }
            }



        }
    }

}