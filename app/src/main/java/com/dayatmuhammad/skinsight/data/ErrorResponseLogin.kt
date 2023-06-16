package com.dayatmuhammad.skinsight.data

import com.google.gson.annotations.SerializedName

data class ErrorResponseLogin(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
