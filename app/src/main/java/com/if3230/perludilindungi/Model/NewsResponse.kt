package com.if3230.perludilindungi.Model

data class NewsResponse(
	val success: Boolean,
	val message: String,
	val count_total: Int,
	val results: List<News>,
)
