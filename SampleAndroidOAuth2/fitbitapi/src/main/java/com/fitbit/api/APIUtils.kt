package com.fitbit.api

import android.app.Activity
import com.fitbit.api.exceptions.MissingScopesException
import com.fitbit.api.exceptions.TokenExpiredException
import com.fitbit.authentication.AccessToken
import com.fitbit.authentication.AuthenticationManager
import com.fitbit.authentication.Scope
import java.util.*

object APIUtils {
    @JvmStatic
    @Throws(MissingScopesException::class, TokenExpiredException::class)
    fun validateToken(contextActivity: Activity?, accessToken: AccessToken, vararg scopes: Scope?) {
        val requiredScopes: MutableSet<Scope> = HashSet(Arrays.asList(*scopes))
        requiredScopes.removeAll(accessToken.scopes)
        if (!requiredScopes.isEmpty()) {
            throw MissingScopesException(requiredScopes)
        }
        if (accessToken.hasExpired()) {
            // Check to see if we should logout
            if (AuthenticationManager.getAuthenticationConfiguration().isLogoutOnAuthFailure) {
                AuthenticationManager.logout(contextActivity)
            } else {
                throw TokenExpiredException()
            }
        }
    }
}