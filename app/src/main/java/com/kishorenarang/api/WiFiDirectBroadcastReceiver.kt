package com.kishorenarang.api

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.p2p.WifiP2pManager
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.kishorenarang.ui.send.SendFragment
import com.kishorenarang.ui.send.tabs.Apps
import com.tarlochan.inshareapp.ReceiveFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
class WiFiDirectBroadcastReceiver() : BroadcastReceiver(), Parcelable {



    var manager:WifiP2pManager? = null
    var channel:WifiP2pManager.Channel?=null
    var fragment:com.tarlochan.inshareapp.SendFragment? = null
    private constructor(manager: WifiP2pManager,  channel: WifiP2pManager.Channel,  fragment: com.tarlochan.inshareapp.SendFragment) : this() {
        this.manager = manager
        this.channel = channel
        this.fragment = fragment

    }


    companion object{

        var obj:WiFiDirectBroadcastReceiver?=  null
        fun create(manager: WifiP2pManager,  channel: WifiP2pManager.Channel,  fragment: com.tarlochan.inshareapp.SendFragment):WiFiDirectBroadcastReceiver
        {
            if(obj==null)
                obj = WiFiDirectBroadcastReceiver(manager, channel, fragment)

            return obj!!
        }

    }

    private  val TAG = "WiFiDirectBroadcastRece"

    constructor(parcel: Parcel) : this() {

    }

    override fun onReceive(p0: Context?, p1: Intent?) {



        val action:String = p1!!.action!!

        Log.d(TAG, "onReceive: Repeating Itself. ")
        when(action)
        {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = p1.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if(state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                {


                    Toast.makeText(p0, "Wifi is ON", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(p0, "Wifi is OFF", Toast.LENGTH_LONG).show()

                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {

                if(manager!=null)
                {
                    if (ActivityCompat.checkSelfPermission(
                            fragment!!.requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(fragment!!.requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
                        Toast.makeText(fragment!!.requireContext(), "Location permission not granted",Toast.LENGTH_LONG).show()

                        return
                    }

                }
                manager?.requestPeers(channel, fragment!!.peerListListener)
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {}
            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {}


        }

    }


}