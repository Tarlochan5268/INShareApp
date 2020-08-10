package com.kishorenarang.api

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

class WifiUtility
{
    private constructor()
    {

    }
    private var context:Context? = null
    companion object{
        private var obj:WifiUtility? = null
        fun create(context: Context):WifiUtility
        {
            if(obj==null)
            {
                obj = WifiUtility()
                obj!!.context = context

            }
            return obj!!
        }
    }




    fun connectToPeer( device:WifiP2pDevice, manager: WifiP2pManager, channel:WifiP2pManager.Channel)
    {
        val config:WifiP2pConfig = WifiP2pConfig()
        config.deviceAddress = device.deviceAddress

        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(
                context,
                "Location permission not granted",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        manager.connect(channel, config,object:WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Toast.makeText(
                    context,
                    "Connected to "+device.deviceName,
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onFailure(p0: Int) {
                Toast.makeText(
                    context,
                    "Failed to connect",
                    Toast.LENGTH_LONG
                ).show()
            }
        } )

    }



}