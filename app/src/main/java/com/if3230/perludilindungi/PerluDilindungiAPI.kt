package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest
import com.if3230.perludilindungi.Model.CheckInResponse
import com.if3230.perludilindungi.Model.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PerluDilindungiAPI {
	@POST("check-in")
	fun doCheckIn(@Body checkInRequest: CheckInRequest): Call<CheckInResponse>

	@GET("get-news")
	suspend fun getNews(): Response<NewsResponse>

	companion object {
		var perluDilindungiAPI: PerluDilindungiAPI? = null

		fun getInstance(): PerluDilindungiAPI {
			if (perluDilindungiAPI == null) {
				val retrofit = Retrofit.Builder()
					.baseUrl("https://perludilindungi.herokuapp.com/api/")
					.addConverterFactory(GsonConverterFactory.create())
					.build()

				perluDilindungiAPI = retrofit.create(PerluDilindungiAPI::class.java)
			}
			return perluDilindungiAPI!!
		}
	}
}