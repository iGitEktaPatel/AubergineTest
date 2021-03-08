package com.fitbit.api.services

import androidx.fragment.app.FragmentActivity
import androidx.loader.content.Loader
import com.fitbit.api.exceptions.MissingScopesException
import com.fitbit.api.exceptions.TokenExpiredException
import com.fitbit.api.loaders.ResourceLoader
import com.fitbit.api.loaders.ResourceLoaderFactory
import com.fitbit.api.loaders.ResourceLoaderResult
import com.fitbit.api.models.DailyActivitySummary
import com.fitbit.authentication.Scope
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object ActivityService {
    private const val ACTIVITIES_URL = "https://api.fitbit.com/1/user/-/activities/date/%s.json"
    private val USER_ACTIVITIES_LOADER_FACTORY = ResourceLoaderFactory(ACTIVITIES_URL, DailyActivitySummary::class.java)
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    @JvmStatic
    @Throws(MissingScopesException::class, TokenExpiredException::class)
    fun getDailyActivitySummaryLoader(activityContext: FragmentActivity?, date: Date?): Loader<ResourceLoaderResult<DailyActivitySummary?>> {
        return USER_ACTIVITIES_LOADER_FACTORY.newResourceLoader(activityContext, arrayOf(Scope.activity), dateFormat.format(date))
    }
}