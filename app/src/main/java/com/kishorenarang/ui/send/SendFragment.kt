package com.kishorenarang.ui.send

import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kishorenarang.adapters.TabsPagerAdapter
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.activity_main.*
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

        TabLayoutMediator(tabs, viewpager, object:TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                Log.d(TAG, "onConfigureTab: "+tab.text)
            }
        })


       val wifiManager = requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if(wifiManager.isWifiEnabled)
        {
            //Snackbar.make(container, "Wifi is Enabled", Snackbar.LENGTH_LONG).show()
            Toast.makeText(context, "Wifi is Enabled",Toast.LENGTH_LONG).show()
        }
        else
        {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                val openSettings = Intent(Settings.Panel.ACTION_WIFI)
                startActivityForResult(openSettings, 0)

            }
            else
            {
                wifiManager.setWifiEnabled(true)
                Toast.makeText(context, "Wifi was just  Enabled",Toast.LENGTH_LONG).show()

            }

        }



    }

}