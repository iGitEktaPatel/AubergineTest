package com.fitbit.sampleandroidoauth2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.fitbit.authentication.AuthenticationManager
import com.fitbit.authentication.Scope
import com.fitbit.sampleandroidoauth2.fragments.ActivitiesFragment
import com.fitbit.sampleandroidoauth2.fragments.InfoFragment
import java.util.*

class UserDataPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    private val fragments: MutableList<InfoFragment<*>> = ArrayList()
    private fun containsScope(scope: Scope): Boolean {
        return AuthenticationManager.getCurrentAccessToken().scopes.contains(scope)
    }

    override fun getItem(position: Int): Fragment {
        return /*if (position>=fragments.size)
            null
        else*/ fragments[position]
    }


    override fun getCount(): Int {
        return fragments.size
    }

    fun getTitleResourceId(index: Int): Int {
        return fragments[index].titleResourceId
    }

    init {
        fragments.clear()
        //        if (containsScope(Scope.profile)) {
//            fragments.add(new ProfileFragment());
//        }
//        if (containsScope(Scope.settings)) {
//            fragments.add(new DeviceFragment());
//        }
        if (containsScope(Scope.activity)) {
            fragments.add(ActivitiesFragment())
        }
        //        if (containsScope(Scope.weight)) {
//            fragments.add(new WeightLogFragment());
//        }
    }
}