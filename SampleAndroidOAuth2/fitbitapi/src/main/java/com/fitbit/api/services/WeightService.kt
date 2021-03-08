//package com.fitbit.api.services
//
//import android.app.Activity
//import android.content.Loader
//import com.fitbit.api.exceptions.MissingScopesException
//import com.fitbit.api.exceptions.TokenExpiredException
//import com.fitbit.api.loaders.ResourceLoaderFactory
//import com.fitbit.api.loaders.ResourceLoaderResult
//import com.fitbit.api.models.WeightLogs
//import com.fitbit.authentication.Scope
//import java.text.DateFormat
//import java.text.SimpleDateFormat
//import java.util.*
//
//object WeightService {
//    private const val WEIGHT_URL = "https://api.fitbit.com/1/user/-/body/log/weight/date/%s/%s.json"
//    private val WEIGHT_LOG_LOADER_FACTORY = ResourceLoaderFactory(WEIGHT_URL, WeightLogs::class.java)
//    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//    @Throws(MissingScopesException::class, TokenExpiredException::class)
//    fun getWeightLogLoader(activityContext: Activity?, startDate: Date?, calendarDateType: Int, number: Int): Loader<ResourceLoaderResult<WeightLogs>> {
//        var periodSuffix = "d"
//        when (calendarDateType) {
//            Calendar.WEEK_OF_YEAR -> periodSuffix = "w"
//            Calendar.MONTH -> periodSuffix = "m"
//        }
//        return WEIGHT_LOG_LOADER_FACTORY.newResourceLoader(
//                activityContext, arrayOf(Scope.weight),
//                dateFormat.format(startDate), String.format(Locale.US, "%d%s", number, periodSuffix))
//    }
//}