package com.kishorenarang.adapters

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kishorenarang.api.App
import com.kishorenarang.api.FileItem
import com.tarlochan.inshareapp.R
import java.io.Serializable

class FilesAdapter(val filesList:List<FileItem>, val context: Context?): Serializable,RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    //var checkBoxStateArray = SparseBooleanArray()
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        var fileIcon: ImageView = view.findViewById<ImageView>(R.id.ivFileIcon)
        val fileName: TextView = view.findViewById<TextView>(R.id.tvFileName)
        val fileLocation: TextView = view.findViewById<TextView>(R.id.tvFileLocation)
        val fileSize: TextView = view.findViewById<TextView>(R.id.tvFileSize)
        val containerLayout: LinearLayout = view.findViewById<LinearLayout>(R.id.containLayout)

        init {
            /*containerLayout.setOnClickListener {
                if(!checkBoxStateArray.get(adapterPosition,false))
                {   //checkbox checked
                    containerLayout.setBackgroundColor(Color.rgb(255, 150, 52))
                    //stores checkbox states and position
                    checkBoxStateArray.put(adapterPosition,true)
                }
                else
                {   //checkbox unchecked
                    containerLayout.setBackgroundColor(Color.WHITE)
                    //stores checkbox states and position.
                    checkBoxStateArray.put(adapterPosition,false)
                }
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.files_card,parent,false)
        return ViewHolder(cardView)
    }

    override fun getItemCount() = filesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.fileIcon.setImageDrawable(filesList[position].icon)
        holder.fileName.text = filesList[position].name
        holder.fileLocation.text = filesList[position].location
        holder.fileSize.text = filesList[position].size

        /*if(!checkBoxStateArray.get(position,false))
        {//checkbox unchecked.
            holder.containerLayout.setBackgroundColor(Color.WHITE)
        }
        else
        {//checkbox checked
            holder.containerLayout.setBackgroundColor(Color.rgb(255, 150, 52))
        }
        //gets position from data object
        var dataPosition = appsList[position].hashCode()*/
    }
}