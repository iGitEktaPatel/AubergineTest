//package com.fitbit.api.services
//
//import android.app.Activity
//import android.content.Loader
//import com.fitbit.api.exceptions.MissingScopesException
//import com.fitbit.api.exceptions.TokenExpiredException
//import com.fitbit.api.loaders.ResourceLoaderFactory
//import com.fitbit.api.loaders.ResourceLoaderResult
//import com.fitbit.api.models.UserContainer
//import com.fitbit.authentication.Scope
//
//object UserService {
//    private const val USER_URL = "https://api.fitbit.com/1/user/-/profile.json"
//    private val USER_PROFILE_LOADER_FACTORY = ResourceLoaderFactory(USER_URL, UserContainer::class.java)
//    @Throws(MissingScopesException::class, TokenExpiredException::class)
//    fun getLoggedInUserLoader(activityContext: Activity?): Loader<ResourceLoaderResult<UserContainer>> {
//        return USER_PROFILE_LOADER_FACTORY.newResourceLoader(activityContext, arrayOf(Scope.profile))
//    }
//}