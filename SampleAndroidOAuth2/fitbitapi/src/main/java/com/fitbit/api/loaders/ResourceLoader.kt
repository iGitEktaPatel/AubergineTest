package com.fitbit.api.loaders

import android.app.Activity
import android.os.Handler
import androidx.loader.content.AsyncTaskLoader
import com.fitbit.api.APIUtils.validateToken
import com.fitbit.authentication.AuthenticationManager
import com.fitbit.authentication.Scope
import com.google.gson.Gson
import java.lang.Exception
import java.util.*

class ResourceLoader<T>(private val contextActivity: Activity, private val url: String, private val requiredScopes: Array<Scope>, private val handler: Handler, private val classType: Class<T>) : AsyncTaskLoader<ResourceLoaderResult<T?>>(contextActivity) {
    override fun loadInBackground(): ResourceLoaderResult<T?> {
        return try {
            validateToken(contextActivity, AuthenticationManager.getCurrentAccessToken(), *requiredScopes)
            val request = AuthenticationManager
                    .createSignedRequest()
                    .setContentType("Application/json")
                    .setUrl(url)
                    .build()
            val response = request.execute()
            val responseCode = response.statusCode
            val json = response.bodyAsString
            if (response.isSuccessful) {
                val resource = Gson().fromJson(json, classType)
                ResourceLoaderResult.onSuccess(resource)
            } else {
                if (responseCode == 401) {
                    if (AuthenticationManager.getAuthenticationConfiguration().isLogoutOnAuthFailure) {
                        // Token may have been revoked or expired
                        handler.post { AuthenticationManager.logout(contextActivity) }
                    }
                    ResourceLoaderResult.onLoggedOut()
                } else {
                    val errorMessage = String.format(Locale.ENGLISH,
                            "Error making request:%s\tHTTP Code:%d%s\tResponse Body:%s%s%s\n",
                            EOL,
                            responseCode,
                            EOL,
                            EOL,
                            json,
                            EOL)
                    ResourceLoaderResult.onError(errorMessage)
                }
            }
        } catch (e: Exception) {
            ResourceLoaderResult.onException(e)
        }
    }

    companion object {
        private val EOL = System.getProperty("line.separator")
    }
}