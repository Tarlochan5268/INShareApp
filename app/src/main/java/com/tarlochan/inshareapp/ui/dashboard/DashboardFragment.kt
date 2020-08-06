package com.tarlochan.inshareapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tarlochan.inshareapp.R
import com.tarlochan.inshareapp.ReceiveFragment
import com.tarlochan.inshareapp.SendFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment(), View.OnClickListener {


    var navController:NavController? = null
    private lateinit var dashboardViewModel: DashboardViewModel
    private  val TAG = "DashboardFragment"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {



        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val btnSend: Button =  root.findViewById<Button>(R.id.btnSend)
        btnSend.isActivated = false
        btnSend.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.nav_host_fragment, SendFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val btnReceive: Button =  root.findViewById<Button>(R.id.btnReceive)
        btnReceive.isActivated = false
        btnReceive.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.nav_host_fragment, ReceiveFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        navController = Navigation.findNavController(view)
        btnSend.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        when(p0!!.id)
        {
            R.id.btnSend -> navController!!.navigate(R.id.action_navigation_dashboard_to_sendFragment)
        }
    }
}