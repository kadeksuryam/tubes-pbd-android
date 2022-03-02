package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import com.if3230.perludilindungi.Model.NewsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PerduliLindungiAPI {
    @POST("check-in")
    fun doCheckIn(@Body checkInRequest: CheckInRequest): Call<CheckInResponse>
    @GET("get-news")
    fun getNews(): Call<NewsResponse>

    companion object {
        var perduliLindungiAPI: PerduliLindungiAPI? = null

        fun getInstance(): PerduliLindungiAPI {
            if (perduliLindungiAPI == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://perludilindungi.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                perduliLindungiAPI = retrofit.create(PerduliLindungiAPI::class.java)
            }
            return perduliLindungiAPI!!
        }
    }
}