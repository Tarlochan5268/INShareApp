package com.kishorenarang.ui.send.tabs

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.kishorenarang.api.App
import com.kishorenarang.api.WiFiDirectBroadcastReceiver
import com.tarlochan.inshareapp.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Apps.newInstance] factory method to
 * create an instance of this fragment.
 */
class Apps : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        manager = requireContext().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager!!.initialize(requireContext(), Looper.getMainLooper(), null)

        val wifiManager:WifiManager = requireContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
       {
           val intent = Intent(Settings.Panel.ACTION_WIFI)
           requireActivity().startActivity(intent)

       }
        else
       {
           wifiManager.setWifiEnabled(true);
           Log.d(TAG, "onCreateView: WIFI ENABLED")

       }

        receiver = WiFiDirectBroadcastReceiver.create(manager!!, channel!!, this)

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)

        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

//edits by kishore narang


        var manager:WifiP2pManager? = null
        var channel:WifiP2pManager.Channel? = null
        val intentFilter:IntentFilter = IntentFilter()
        var receiver:BroadcastReceiver? = null
        private  val TAG = "Apps"
        var peers:MutableList<WifiP2pDevice> = arrayListOf()


        val peerListListener:WifiP2pManager.PeerListListener = object : WifiP2pManager.PeerListListener {
            override fun onPeersAvailable(p0: WifiP2pDeviceList?) {

                if(!p0!!.deviceList.equals(peers))
                {
                    peers.clear()
                    peers.addAll(p0!!.deviceList)
                    Log.d(TAG, "onPeersAvailable: "+peers.toString())
                }
            }

        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
                Toast.makeText(requireContext(), "Location permission not granted",Toast.LENGTH_LONG).show()
                return
            }
            manager!!.discoverPeers(channel,object : WifiP2pManager.ActionListener{
                override fun onSuccess() {
                    Log.d(TAG, "onSuccess: Discovery Succees")
                }

                override fun onFailure(p0: Int) {
                    Log.d(TAG, "onFailure: Discouvery Failed "+p0)
                }
            })








    }


    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(receiver, intentFilter)
        Log.d(TAG, "onResume: RECEIVER HAS BEEN REGISTERED")
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
        Log.d(TAG, "onPause: UNREGISTERING RECEIVER")
    }


    //end of edits by kishore narang











    //creating a function for all installed apps;

    fun getInstalledApps():List<App>
    {
        var list = arrayListOf<App>()
        val pm = requireActivity().packageManager!!
        val packageInfo:List<PackageInfo> = pm.getInstalledPackages(0)
        for(pack in packageInfo)
        {
            if(!isSystemPackage(pack))
            {
                list.add(App(pack.applicationInfo.loadLabel(pm).toString(), pack.applicationInfo.loadIcon(pm), pack.applicationInfo.packageName))
            }
        }
        return list
    }

    private fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Apps.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Apps().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}