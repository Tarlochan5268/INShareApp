package com.kishorenarang.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.api.App
import com.tarlochan.inshareapp.R

class AppsAdapter(val appsList:List<App>, val context: Context?): RecyclerView.Adapter<AppsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var appIcon: ImageView = view.findViewById<ImageView>(R.id.ivAppIcon)
        val appName: TextView = view.findViewById<TextView>(R.id.tvAppName)
        val appPackage: TextView = view.findViewById<TextView>(R.id.tvAppPackage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.apps_card,parent,false)
        return ViewHolder(cardView)
    }

    override fun getItemCount() = appsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.appIcon.setImageDrawable(appsList[position].icon)
        holder.appName.text = appsList[position].name
        holder.appPackage.text = appsList[position].packageName
    }
}