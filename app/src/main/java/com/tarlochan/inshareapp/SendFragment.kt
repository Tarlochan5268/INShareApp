package com.tarlochan.inshareapp

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.kishorenarang.api.WiFiDirectBroadcastReceiver

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var runnable:Runnable

/**
 * A simple [Fragment] subclass.
 * Use the [SendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SendFragment : Fragment() {
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


        manager = requireContext().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager!!.initialize(requireContext(), Looper.getMainLooper(), null)

        val wifiManager: WifiManager = requireContext().getApplicationContext().getSystemService(
            Context.WIFI_SERVICE) as WifiManager

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


        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_send, container, false)

        val im1: ImageView =  root.findViewById<ImageView>(R.id.im1)
        val im2: ImageView =  root.findViewById<ImageView>(R.id.im2)
        val im3: ImageView =  root.findViewById<ImageView>(R.id.im3)
        val im4: ImageView =  root.findViewById<ImageView>(R.id.im4)
        val im5: ImageView =  root.findViewById<ImageView>(R.id.im5)
        val im6: ImageView =  root.findViewById<ImageView>(R.id.im6)

        val handler: Handler = Handler()
        runnable = Runnable {
            run() {
                imgAnimate(im1,3900,0F)
                imgAnimate(im2,3600,4F)
                imgAnimate(im3,3300,7F)
                imgAnimate(im4,3000,10F)
                imgAnimate(im5,2700,12F)
                imgAnimate(im6,2400,15F)

                handler.postDelayed(runnable,3900)
            }
        }

        runnable.run()

        return root
    }

    private fun imgAnimate(img: ImageView, duration: Long, elevation: Float)
    {
        img.animate().scaleX(5f).scaleY(5f).alpha(0f).setDuration(duration).withEndAction(
            Runnable {
                run() {
                    img.scaleX = 1f
                    img.scaleY = 1f
                    img.alpha = 1f
                    img.elevation = elevation
                }
            }
        )
    }



//edits by kishore narang


    var manager: WifiP2pManager? = null
    var channel: WifiP2pManager.Channel? = null
    val intentFilter: IntentFilter = IntentFilter()
    var receiver: BroadcastReceiver? = null
    private  val TAG = "Apps"
    var peers:MutableList<WifiP2pDevice> = arrayListOf()


    val peerListListener: WifiP2pManager.PeerListListener = object : WifiP2pManager.PeerListListener {
        override fun onPeersAvailable(p0: WifiP2pDeviceList?) {

            if(!p0!!.deviceList.equals(peers))
            {
                peers.clear()
                peers.addAll(p0!!.deviceList)
                Log.d(TAG, "onPeersAvailable: "+peers.toString())
            }


            // WifiUtility.create(requireContext()).connectToPeer(peers[0],manager!!,channel!!)

        }



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            Toast.makeText(
                requireContext(),
                "Location permission not granted",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        manager!!.discoverPeers(channel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                Log.d(TAG, "onSuccess: Discovery Succees")
            }

            override fun onFailure(p0: Int) {
                Log.d(TAG, "onFailure: Discouvery Failed " + p0)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SendFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SendFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}