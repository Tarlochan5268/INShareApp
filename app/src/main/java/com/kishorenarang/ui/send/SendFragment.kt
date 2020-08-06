package com.kishorenarang.ui.send

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kishorenarang.adapters.TabsPagerAdapter
import com.tarlochan.inshareapp.R
import kotlinx.android.synthetic.main.send_fragment.*

class SendFragment : Fragment() {


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = TabsPagerAdapter(requireActivity())

        TabLayoutMediator(tabs, viewpager, object:TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                Log.d(TAG, "onConfigureTab: "+tab.text)
            }
        })

    }

}