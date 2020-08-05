package com.kishorenarang.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface NewsAPI
{


    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca")
    fun getNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=sports")
    fun getSportsNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=business")
    fun getBusinessNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=politics")
    fun getPoliticsNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=technology")
    fun getTechnologyNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=entertainment")
    fun getEntertainmentNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=general")
    fun getGeneralNews(): Call<ResponseBody>;

    @GET("top-headlines?apiKey=c28790c31da3411d86d3842c623b5cfa&country=ca&category=science")
    fun getScienceNews(): Call<ResponseBody>;



}