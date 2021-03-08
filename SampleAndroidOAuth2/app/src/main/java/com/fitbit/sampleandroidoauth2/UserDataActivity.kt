package com.fitbit.sampleandroidoauth2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.fitbit.authentication.AuthenticationManager
import com.fitbit.sampleandroidoauth2.databinding.ActivityUserDataBinding

class UserDataActivity : FragmentActivity() {
    private var binding: ActivityUserDataBinding? = null
    private var userDataPagerAdapter: UserDataPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_data)
        binding?.loading = false
        userDataPagerAdapter = UserDataPagerAdapter(supportFragmentManager)
        binding?.viewPager?.adapter = userDataPagerAdapter
        binding?.viewPager?.addOnPageChangeListener(
                object : SimpleOnPageChangeListener() {
                    override fun onPageSelected(position: Int) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        actionBar!!.setSelectedNavigationItem(position)
                    }
                })

    }

    fun onLogoutClick(view: View?) {
        binding!!.loading = true
        AuthenticationManager.logout(this)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, UserDataActivity::class.java)
        }
    }
}