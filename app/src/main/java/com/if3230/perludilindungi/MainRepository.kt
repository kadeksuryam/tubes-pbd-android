package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest
import retrofit2.http.Query

class MainRepository constructor(private val perluDilindungiAPI: PerluDilindungiAPI) {
	fun doCheckIn(checkInRequest: CheckInRequest) = perluDilindungiAPI.doCheckIn(checkInRequest)
	suspend fun getNews() = perluDilindungiAPI.getNews()
}