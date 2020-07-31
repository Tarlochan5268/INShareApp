package com.kishorenarang.api

import android.util.Log
import okhttp3.*
import java.io.IOException

class BreakingAPI
{

    private  val TAG = "News"
    private constructor()
    {

    }



    companion object {

        private var obj:BreakingAPI? = null;


        fun create() : BreakingAPI?
        {
            if(obj==null)
            {
                obj = BreakingAPI();

            }
            return obj;
        }


    }

    fun call(url:String):String
    {
        var returner = "";
        Log.d("KISHORE","CALL METHOD COLLED")
        Thread(Runnable(){

            val client = OkHttpClient()

            val request = Request.Builder().url(url).build()
            Log.d("KISHORE","INSIDE THREAD")

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    Log.d("KISHORE","Failed");
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {


                    Log.d(TAG, "onResponse: Receeived."+response.body!!.string());
                    returner = response.body!!.string()


                }
            });

        }).start()




        return returner;


    }

}