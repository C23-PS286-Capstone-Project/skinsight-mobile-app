package com.dayatmuhammad.skinsight.data

import com.squareup.moshi.Json

data class RegisterResponse(

	@Json(name="data")
	val data: DataRegister? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="status")
	val status: String? = null
)

data class DataRegister(

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

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="picture")
	val picture: String? = null,

	@Json(name="username")
	val username: String? = null
)
