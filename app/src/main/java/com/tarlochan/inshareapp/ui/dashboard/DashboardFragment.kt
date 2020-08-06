package com.tarlochan.inshareapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.tarlochan.inshareapp.R
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