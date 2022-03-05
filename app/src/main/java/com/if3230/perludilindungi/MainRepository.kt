package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest

class MainRepository constructor(private val perluDilindungiAPI: PerluDilindungiAPI) {
	fun doCheckIn(checkInRequest: CheckInRequest) = perluDilindungiAPI.doCheckIn(checkInRequest)
	suspend fun getNews() = perluDilindungiAPI.getNews()
	suspend fun getProvince() = perluDilindungiAPI.getProvince()
	suspend fun getCity(startId: String) = perluDilindungiAPI.getCity(startId)
	suspend fun getFaskes(province: String, city: String) =
		perluDilindungiAPI.getFaskes(province, city)
}