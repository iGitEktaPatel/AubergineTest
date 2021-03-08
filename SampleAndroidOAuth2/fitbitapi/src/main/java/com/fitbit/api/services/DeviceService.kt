//package com.fitbit.api.services
//
//import android.app.Activity
//import android.content.Loader
//import com.fitbit.api.exceptions.MissingScopesException
//import com.fitbit.api.exceptions.TokenExpiredException
//import com.fitbit.api.loaders.ResourceLoaderFactory
//import com.fitbit.api.loaders.ResourceLoaderResult
//import com.fitbit.api.models.Device
//import com.fitbit.authentication.Scope
//
//object DeviceService {
//    private const val DEVICE_URL = "https://api.fitbit.com/1/user/-/devices.json"
//    private val USER_DEVICES_LOADER_FACTORY = ResourceLoaderFactory(DEVICE_URL, Array<Device>::class.java)
//    @Throws(MissingScopesException::class, TokenExpiredException::class)
//    fun getUserDevicesLoader(activityContext: Activity?): Loader<ResourceLoaderResult<Array<Device?>>> {
//        return USER_DEVICES_LOADER_FACTORY.newResourceLoader(activityContext, arrayOf(Scope.settings))
//    }
//}