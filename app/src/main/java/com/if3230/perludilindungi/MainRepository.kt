package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest

class MainRepository constructor(private val perluDilindungiAPI: PerluDilindungiAPI) {
	fun doCheckIn(checkInRequest: CheckInRequest) = perluDilindungiAPI.doCheckIn(checkInRequest)
	fun getNews() = perluDilindungiAPI.getNews()
}