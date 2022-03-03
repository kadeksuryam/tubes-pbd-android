package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.*
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

	@GET("api/get-news")
	suspend fun getNews(): Response<NewsResponse>

	@GET("get-province")
	suspend fun getProvince(): Response<ProvinceResponse>

	companion object {
		var perluDilindungiAPI: PerluDilindungiAPI? = null

		fun getInstance(): PerluDilindungiAPI {
			if (perluDilindungiAPI == null) {
				val retrofit = Retrofit.Builder()
					.baseUrl("https://perludilindungi.herokuapp.com/")
					.addConverterFactory(GsonConverterFactory.create())
					.build()

				perluDilindungiAPI = retrofit.create(PerluDilindungiAPI::class.java)
			}
			return perluDilindungiAPI!!
		}
	}
}