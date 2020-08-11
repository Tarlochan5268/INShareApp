package com.kishorenarang.api

import android.graphics.drawable.Drawable
import java.io.Serializable

data class FileItem(val name:String?, val icon: Drawable,val location:String ,val size:String ):Serializable{
}