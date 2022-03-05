package com.if3230.perludilindungi.Model

data class FaskesResponse(
	val success: Boolean,
	val message: String,
	val count_total: Int,
	val data: List<Faskes>
)
