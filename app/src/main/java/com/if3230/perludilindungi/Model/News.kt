package com.if3230.perludilindungi.Model

data class NewsDescription(val __cdata: String)
data class NewsEnclosure(val _url: String, val _length: Long, val _type: String)
data class News(
	val title: String,
	val link: List<String>,
	val guid: String,
	val pubDate: String,
	val description: NewsDescription,
	val enclosure: NewsEnclosure,
)
