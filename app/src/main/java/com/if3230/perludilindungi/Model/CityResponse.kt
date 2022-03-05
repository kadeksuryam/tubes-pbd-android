package com.if3230.perludilindungi.Model

data class CityResponse(
	val curr_val: String,
	val message: String,
	val results: List<City>
)