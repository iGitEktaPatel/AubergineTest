package com.fitbit.sampleandroidoauth2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.fitbit.api.loaders.ResourceLoaderResult
import com.fitbit.api.loaders.ResourceLoaderResult.ResultType
import com.fitbit.api.models.ActivityData
import com.fitbit.sampleandroidoauth2.R
import com.fitbit.sampleandroidoauth2.adapter.ActivityListAdapter
import com.fitbit.sampleandroidoauth2.databinding.LayoutInfoBinding
import com.fitbit.sampleandroidoauth2.util.EndlessRecyclerViewScrollListener
import java.text.NumberFormat
import java.util.*

abstract class InfoFragment<T> : Fragment(), LoaderManager.LoaderCallbacks<ResourceLoaderResult<T>>, OnRefreshListener {
    protected var binding: LayoutInfoBinding? = null
    protected val TAG = InfoFragment::class.java.simpleName
    private var offset = 0
    private var lastIndex = 0
    private var activityData: MutableList<ActivityData>? = null
    private val activityDisplayData: MutableList<ActivityData>? = null
    private var activityListAdapter: ActivityListAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_info, container, false)
        binding?.titleText = titleResourceId
//        setMainText(activity!!.getString(R.string.no_data))
        binding?.swipeRefreshLayout?.setOnRefreshListener(this)
        binding?.loading = true
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        loaderManager.initLoader(loaderId, null, this).forceLoad()
    }

    override fun onLoadFinished(loader: Loader<ResourceLoaderResult<T>>, data: ResourceLoaderResult<T>) {
        binding!!.swipeRefreshLayout.isRefreshing = false
        binding!!.loading = false
        when (data.resultType) {
            ResultType.ERROR -> Toast.makeText(activity, R.string.error_loading_data, Toast.LENGTH_LONG).show()
            ResultType.EXCEPTION -> {
                Log.e(TAG, "Error loading data", data.exception)
                Toast.makeText(activity, R.string.error_loading_data, Toast.LENGTH_LONG).show()
            }
        }
    }

    abstract val titleResourceId: Int
    protected abstract val loaderId: Int
    override fun onLoaderReset(loader: Loader<ResourceLoaderResult<T>>) {
        //no-op
    }

    override fun onRefresh() {
        loaderManager.getLoader<Any>(loaderId)!!.forceLoad()
    }

    private fun formatNumber(number: Number): String {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
    }

    private fun isImageUrl(string: String): Boolean {


        return string.startsWith("http", true) &&
                (string.endsWith("jpg", true)
                        || string.endsWith("gif", true)
                        || string.endsWith("png", true))
    }

    /*protected fun printKeys(stringBuilder: StringBuilder, `object`: Any?) {
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(Gson().toJson(`object`))
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val value = jsonObject[key]
                if (value !is JSONObject
                        && value !is JSONArray) {
                    stringBuilder.append("&nbsp;&nbsp;&nbsp;&nbsp;<b>")
                    stringBuilder.append(key)
                    stringBuilder.append(":</b>&nbsp;")
                    if (value is Number) {
                        stringBuilder.append(formatNumber(value))
                    } else if (isImageUrl(value.toString())) {
                        stringBuilder.append("<br/>")
                        stringBuilder.append("<center><img src=\"")
                        stringBuilder.append(value.toString())
                        stringBuilder.append("\" width=\"150\" height=\"150\"></center>")
                    } else {
                        stringBuilder.append(value.toString())
                    }
                    stringBuilder.append("<br/>")
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }*/

    /* protected fun setMainText(text: String?) {
 //        binding.webview.loadData(text, "text/html", "UTF-8");
     }*/

    protected fun setMainActivityList(activities: MutableList<ActivityData>?) {
        if (activityData != null) activityData!!.clear()
        if (activityDisplayData != null) activityDisplayData.clear()
        activityData = activities
        if (activities == null || activities.isEmpty()) {
            binding!!.txtNoData.visibility = View.VISIBLE
            binding!!.rvActivityList.visibility = View.GONE
        } else {
            binding!!.txtNoData.visibility = View.GONE
            binding!!.rvActivityList.visibility = View.VISIBLE
        }
        binding!!.progressLoadMore.visibility = View.GONE
        binding!!.rvActivityList.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding!!.rvActivityList.layoutManager = mLayoutManager
        activityListAdapter = ActivityListAdapter(activityDisplayData, activity)
        binding!!.rvActivityList.adapter = activityListAdapter
        binding!!.rvActivityList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(mLayoutManager, activity, activities!!.size) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (offset > 0) {
                    binding!!.progressLoadMore.visibility = View.VISIBLE
                    appendListItem()
                } else {
                    //all data download successfully.
                }
            }
        })
    }

    private fun appendListItem() {
        if (offset == 0) {
            lastIndex = 0
        } else offset++
        val lastPos = lastIndex
        var i = lastPos
        while (i < lastPos + 10 || i < activityData!!.size) {
            activityDisplayData?.add(activityData!![i])
            lastIndex = i
            i++
        }
        if (activityListAdapter != null) {
            activityListAdapter?.lists = activityDisplayData ?: ArrayList()
            activityListAdapter?.notifyDataSetChanged()
        }
        binding!!.progressLoadMore.visibility = View.GONE
    }
}