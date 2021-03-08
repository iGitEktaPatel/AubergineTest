package com.fitbit.sampleandroidoauth2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fitbit.api.models.ActivityData
import com.fitbit.sampleandroidoauth2.R
import java.util.*
import kotlin.collections.ArrayList

class ActivityListAdapter(list: List<ActivityData>?, mContext: Context?) : RecyclerView.Adapter<ActivityListAdapter.ViewHolder>() {
    var lists: List<ActivityData?> = ArrayList()
    private val mContext: Context?


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
                R.layout.item_activity_list, parent, false)
        vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder?)!!.txtName.text = lists[position]?.name
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    /***
     * @info view holder
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtName: TextView

        init {
            txtName = v.findViewById<View>(R.id.txtTitle) as TextView
        }
    }

    /***
     * @param list = list of activity
     * @param mContext = activity context
     */
    init {
        lists = list?:ArrayList()
        this.mContext = mContext
    }
}