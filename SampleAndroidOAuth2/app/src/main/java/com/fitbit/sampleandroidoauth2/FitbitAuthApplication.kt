package com.fitbit.sampleandroidoauth2

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.fitbit.authentication.*
import java.lang.Exception

class FitbitAuthApplication : Application() {
    /**
     * 1. When the application starts, load our keys and configure the AuthenticationManager
     */
    override fun onCreate() {
        super.onCreate()
        AuthenticationManager.configure(this, generateAuthenticationConfiguration(this, RootActivity::class.java))
    }

    companion object {
        /**
         * These client credentials come from creating an app on https://dev.fitbit.com.
         *
         *
         * To use with your own app, register as a developer at https://dev.fitbit.com, create an application,
         * set the "OAuth 2.0 Application Type" to "Client", enter a word for the redirect url as a url
         * (like `https://finished` or `https://done` or `https://completed`, etc.), and save.
         *
         *
         */
        //!! THIS SHOULD BE IN AN ANDROID KEYSTORE!! See https://developer.android.com/training/articles/keystore.html
        private const val CLIENT_SECRET = "e05ef8e624f1a9d0a3278c277b3ce9ba"
        //    private static final String CLIENT_SECRET = "86401692efd006045a157f45755000d0";
        /**
         * This key was generated using the SecureKeyGenerator [java] class. Run as a Java application (not Android)
         * This key is used to encrypt the authentication token in Android user preferences. If someone decompiles
         * your application they'll have access to this key, and access to your user's authentication token
         */
        //!! THIS SHOULD BE IN AN ANDROID KEYSTORE!! See https://developer.android.com/training/articles/keystore.html
        private const val SECURE_KEY = "CVPdQNAT6fBI4rrPLEn9x0+UV84DoqLFiNHpKOPLRW0="

        /**
         * This method sets up the authentication config needed for
         * successfully connecting to the Fitbit API. Here we include our client credentials,
         * requested scopes, and  where to return after login
         */
        fun generateAuthenticationConfiguration(context: Context, mainActivityClass: Class<out Activity?>?): AuthenticationConfiguration {
            return try {
                val ai = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                val bundle = ai.metaData

                // Load clientId and redirectUrl from application manifest
                val clientId = bundle.getString("com.fitbit.sampleandroidoauth2.CLIENT_ID")
                val redirectUrl = bundle.getString("com.fitbit.sampleandroidoauth2.REDIRECT_URL")
                val CLIENT_CREDENTIALS = ClientCredentials(clientId, CLIENT_SECRET, redirectUrl)
                AuthenticationConfigurationBuilder()
                        .setClientCredentials(CLIENT_CREDENTIALS)
                        .setEncryptionKey(SECURE_KEY)
                        .setTokenExpiresIn(2592000L) // 30 days
                        .setBeforeLoginActivity(Intent(context, mainActivityClass))
                        .addRequiredScopes(Scope.profile, Scope.settings)
                        .addOptionalScopes(Scope.activity, Scope.weight)
                        .setLogoutOnAuthFailure(true)
                        .build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}