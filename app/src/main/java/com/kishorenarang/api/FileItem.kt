package com.kishorenarang.api

import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class FileItem(val name:String?, val icon: Int,val location:String ,val size:String ):Serializable
{

}


class FileItems:ArrayList<FileItem>(),Serializable
{

}