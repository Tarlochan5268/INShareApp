package com.kishorenarang.ui.send

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kishorenarang.adapters.TabsPagerAdapter
import com.kishorenarang.ui.send.tabs.Files
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.send_fragment.*


class SendFragment : Fragment() {
    private var viewPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private  val TAG = "SendFragment"
    companion object {
        fun newInstance() = SendFragment()
    }

    private lateinit var viewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.send_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SendViewModel::class.java)

    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = TabsPagerAdapter(requireActivity())
        TabLayoutMediator(tabs, viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->

                when (position) {
                    0 -> {
                        tab.text = "APPS"
                    }
                    1 -> {
                        tab.text = "MEDIA"
                    }
                }
            }).attach()

        tabs.setSelectedTabIndicatorColor(Color.WHITE)
        tabs.outlineAmbientShadowColor = Color.DKGRAY
        tabs.setTabTextColors(Color.rgb(229,229,229), Color.WHITE)
        viewPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Toast.makeText(context, "Selected position: ${position}",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }

        viewpager.registerOnPageChangeCallback(viewPageChangeCallback as ViewPager2.OnPageChangeCallback)
        viewpager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_LTR
        tabs.layoutDirection = View.LAYOUT_DIRECTION_LTR

    }
    override fun onDestroy() {
        super.onDestroy()
        //viewpager.unregisterOnPageChangeCallback(this.viewPageChangeCallback!!)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}