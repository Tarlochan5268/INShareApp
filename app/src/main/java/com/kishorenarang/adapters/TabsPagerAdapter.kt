package com.kishorenarang.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kishorenarang.ui.send.tabs.Apps
import com.kishorenarang.ui.send.tabs.Files


class TabsPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {

        return 3;
    }

    override fun createFragment(position: Int): Fragment {

            when(position)
            {
                0 -> return Apps()
                1 -> return Files()

                else -> return Apps()
            }

    }

}