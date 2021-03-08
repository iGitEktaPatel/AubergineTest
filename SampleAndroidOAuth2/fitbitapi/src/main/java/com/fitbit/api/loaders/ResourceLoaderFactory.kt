package com.fitbit.api.loaders

import android.app.Activity
import android.os.Handler
import androidx.loader.content.Loader
import com.fitbit.api.models.DailyActivitySummary
import com.fitbit.authentication.Scope

class ResourceLoaderFactory<T>(private val urlFormat: String, private val classType: Class<T>) {
    fun newResourceLoader(contextActivity: Activity?, requiredScopes: Array<Scope>, vararg pathParams: String?): ResourceLoader<T> {
        var url = urlFormat
        if (pathParams != null && pathParams.size > 0) {
            url = String.format(urlFormat, *pathParams)
        }
        return ResourceLoader(contextActivity!!, url, requiredScopes, Handler(), classType)
    }
}