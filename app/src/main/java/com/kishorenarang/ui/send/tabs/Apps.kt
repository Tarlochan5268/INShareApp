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
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.adapters.AppsAdapter
import com.kishorenarang.api.App
import com.kishorenarang.api.WiFiDirectBroadcastReceiver
import com.kishorenarang.api.WifiUtility
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.fragment_news.*


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



        //return inflater.inflate(R.layout.fragment_apps, container, false)

        val root = inflater.inflate(R.layout.fragment_apps, container, false)
        val recyclerView: RecyclerView =  root.findViewById<RecyclerView>(R.id.rvApp)
        val getAppList = getInstalledApps()
        val adapter = AppsAdapter(getAppList, context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

        val toolBarButton: ImageButton = root.findViewById(R.id.toolbarBtn)
        toolBarButton.setOnClickListener(View.OnClickListener {
            Log.d("--> Array Adapter: ",adapter.checkBoxStateArray.toString())
            val list = arrayListOf<App>()
            list.clear()
            adapter.checkBoxStateArray.forEach { key, value -> if(value)
            {
                list.add(getAppList[key])
            }}

            list.forEach { t -> Log.d("---> Item: ",t.name!!) }
            // --> can transfer this list to next fragment

            list.clear()
            adapter.checkBoxStateArray.clear()
        })

        return root
    }


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