package com.ottamotta.mozoli

import android.app.Activity
import android.app.Dialog
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManager
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface Callback<T> {
    fun onComplete(result: T)
    fun onException(e: Exception?)
}

suspend fun <T> awaitCallback(block: (Callback<T>) -> Unit) : T =
    suspendCancellableCoroutine { cont ->
        block(object : Callback<T> {
            override fun onComplete(result: T) = cont.resume(result)
            override fun onException(e: Exception?) {
                e?.let { cont.resumeWithException(it) }
            }
        })
    }

fun CredentialsManager.getCredentials(callback : Callback<Credentials>) {
    getCredentials(object : BaseCallback<Credentials, CredentialsManagerException> {
        override fun onFailure(error: CredentialsManagerException?) {
            callback.onException(error)
        }

        override fun onSuccess(payload: Credentials?) {
            callback.onComplete(payload!!)
        }
    });
}

fun WebAuthProvider.Builder.start(activity : Activity, callback : Callback<Credentials>) {
    start(activity, object : AuthCallback {
        override fun onSuccess(credentials: Credentials) {
            callback.onComplete(credentials)
        }

        override fun onFailure(dialog: Dialog) {
            callback.onException(ExceptionWithDialog(dialog = dialog))
        }

        override fun onFailure(exception: AuthenticationException?) {
            callback.onException(exception)
        }
    })
}

fun Exception.hasDialog() : Boolean = false

class ExceptionWithDialog(override val cause: Exception? = null, val dialog: Dialog) : Exception(cause) {
    fun hasDialog() : Boolean = true
}

