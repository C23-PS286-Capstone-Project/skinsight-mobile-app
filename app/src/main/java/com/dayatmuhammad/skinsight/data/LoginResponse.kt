package com.dayatmuhammad.skinsight.data

import com.squareup.moshi.Json

data class LoginResponse(

	@Json(name="data")
	val data: DataLogin? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="status")
	val status: String? = null
)

data class DataLogin(

	@Json(name="Type")
	val type: String? = null,

	@Json(name="token")
	val token: String? = null
)
