package com.kishorenarang.api

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import java.lang.StringBuilder

class WifiBroadcastReceiver : BroadcastReceiver()
{
    val wifiManager: WifiManager? = null
    var string:StringBuilder? = null

    override fun onReceive(p0: Context?, p1: Intent?) {



        val action = p1!!.action
        when(action)
        {
            WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE -> {

                var list:List<ScanResult> = wifiManager!!.scanResults
            }
        }


    }

}