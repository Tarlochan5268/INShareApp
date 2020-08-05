package com.kishorenarang.api

import android.util.Log
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import retrofit2.Retrofit
import java.text.SimpleDateFormat

object NewsAPIService
{
    private  val TAG = "NewsAPIService"
        fun create() : NewsAPI
        {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .build()

            val service = retrofit.create(NewsAPI::class.java)
            return service
        }


    fun parse(json:String):ArrayList<Article>
    {
        var arrayList:ArrayList<Article> = arrayListOf()


        val jsonObject = JSONParser().parse(json) as JSONObject
        val articles = jsonObject.get("articles") as JSONArray

        for (i in 0 until articles.size)
        {
            val eachArticle = articles.get(i) as JSONObject

            val author = eachArticle.get("author") as String?
            val title = eachArticle.get("title") as String?
            val description = eachArticle.get("description") as String?
            val url= eachArticle.get("url") as String?
            val image = eachArticle.get("urlToImage") as String?
            val content = eachArticle.get("content") as String?



            var dateString = eachArticle.get("publishedAt") as String?

            dateString = dateString?.substring(0, 10);
            Log.d(TAG, "parse: Date is" +dateString)
            val format = SimpleDateFormat("YYYY-DD-MM")



         val date = format.parse(dateString)

            val article = Article(title, description, url, image, author, date, content)
            arrayList.add(article)



        }

        Log.d(TAG, "parse: "+articles.toString())

        return arrayList

    }
}