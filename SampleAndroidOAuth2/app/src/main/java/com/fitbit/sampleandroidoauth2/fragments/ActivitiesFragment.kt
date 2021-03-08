package com.fitbit.sampleandroidoauth2.fragments

import android.os.Bundle
import androidx.loader.content.Loader
import com.fitbit.api.loaders.ResourceLoaderResult
import com.fitbit.api.models.DailyActivitySummary
import com.fitbit.api.services.ActivityService.getDailyActivitySummaryLoader
import com.fitbit.sampleandroidoauth2.R
import java.util.*

class ActivitiesFragment : InfoFragment<DailyActivitySummary?>() {
    override val titleResourceId: Int
        get() = R.string.activity_info
    override val loaderId: Int
        get() = 3


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<ResourceLoaderResult<DailyActivitySummary?>> {
        return getDailyActivitySummaryLoader(activity, Date())
    }

    override fun onLoadFinished(loader: Loader<ResourceLoaderResult<DailyActivitySummary?>>, data: ResourceLoaderResult<DailyActivitySummary?>) {
        super.onLoadFinished(loader, data)
        if (data.isSuccessful) {
            bindActivityData(data.result)
        }
    }

    private fun bindActivityData(dailyActivitySummary: DailyActivitySummary?) {
        setMainActivityList(dailyActivitySummary?.activities)
    }
}