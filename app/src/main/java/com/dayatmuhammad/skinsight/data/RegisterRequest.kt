package com.dayatmuhammad.skinsight.data

import com.squareup.moshi.Json

data class RegisterRequest(

	@Json(name="birthday")
	val birthday: String? = null,

	@Json(name="password")
	val password: String? = null,

	@Json(name="address")
	val address: String? = null,

	@Json(name="gender")
	val gender: String? = null,

	@Json(name="birthplace")
	val birthplace: String? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="username")
	val username: String? = null
)
