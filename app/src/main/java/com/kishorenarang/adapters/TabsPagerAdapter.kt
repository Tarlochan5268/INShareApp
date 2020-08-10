package com.kishorenarang.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kishorenarang.ui.send.tabs.Apps
import com.kishorenarang.ui.send.tabs.Files


class TabsPagerAdapter(fragmentActivity: FragmentActivity, val navController: NavController):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {

        return 2;
    }

    private  val TAG = "TabsPagerAdapter"

    override fun createFragment(position: Int): Fragment {


        Log.d(TAG, "createFragment: In fragment navControlller was received"+navController.toString())
            when(position)
            {
                0 -> return Apps(navController)
                1 -> return Files()

                else -> return Apps(navController)
            }

    }

}