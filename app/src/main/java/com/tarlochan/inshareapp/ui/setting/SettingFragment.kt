package com.tarlochan.inshareapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tarlochan.inshareapp.EditFragment
import com.tarlochan.inshareapp.R
import com.tarlochan.inshareapp.ReceiveFragment

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        val btnEdit: Button =  root.findViewById<Button>(R.id.btnEdit)
        btnEdit.isActivated = false
        btnEdit.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.nav_host_fragment, EditFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return root
    }
}