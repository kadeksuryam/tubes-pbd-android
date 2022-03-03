package com.if3230.perludilindungi

import com.if3230.perludilindungi.Model.CheckInRequest

class MainRepository constructor(private val perduliLindungiAPI: PerduliLindungiAPI) {
	fun doCheckIn(checkInRequest: CheckInRequest) = perduliLindungiAPI.doCheckIn(checkInRequest)
	fun getNews() = perduliLindungiAPI.getNews()
}