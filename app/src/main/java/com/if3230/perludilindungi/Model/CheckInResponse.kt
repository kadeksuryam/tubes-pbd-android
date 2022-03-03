package com.if3230.perludilindungi.Model

enum class UserStatus {
	black,
	red,
	yellow,
	green
}

data class CheckInResponseData(val userStatus: UserStatus, val reason: String)

data class CheckInResponse(
	val success: String,
	val code: Int,
	val message: String,
	val data: CheckInResponseData
)


