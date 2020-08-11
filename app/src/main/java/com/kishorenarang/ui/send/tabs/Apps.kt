package com.kishorenarang.ui.send.tabs

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.util.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.adapters.AppsAdapter
import com.kishorenarang.api.App
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.fragment_apps.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Apps.newInstance] factory method to
 * create an instance of this fragment.
 */
class Apps(val navController: NavController) : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        val root = inflater.inflate(R.layout.fragment_apps, container, false)
        val recyclerView: RecyclerView =  root.findViewById<RecyclerView>(R.id.rvApp)
        val getAppList = getInstalledApps()
        val adapter = AppsAdapter(getAppList, context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.notifyDataSetChanged()

        val toolBarButton:ImageButton = root.findViewById(R.id.toolbarBtn)
        toolBarButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"-->Pressed Check LogCat",Toast.LENGTH_SHORT).show()
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get all installed apps.


        toolbarBtn.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {

                navController.navigate(R.id.action_sendFragment_to_sendFragment2)
            }

        })


    }
    //creating a function for all installed apps;

    private  val TAG = "Apps"
    private fun getInstalledApps():List<App>
    {
        val list = arrayListOf<App>()
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



}