package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface PerduliLindungiAPI {
    @POST("check-in")
    fun doCheckIn(@Body checkInRequest: CheckInRequest): Call<CheckInResponse>

    companion object {
        var perduliLindungiAPI: PerduliLindungiAPI? = null

        fun getInstance(): PerduliLindungiAPI {
            if (perduliLindungiAPI == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://perludilindungi.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                perduliLindungiAPI = retrofit.create(PerduliLindungiAPI::class.java)
            }
            return perduliLindungiAPI!!
        }
    }
}