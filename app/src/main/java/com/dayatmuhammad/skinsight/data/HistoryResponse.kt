package com.dayatmuhammad.skinsight.data

import com.squareup.moshi.Json

data class HistoryResponse(

	@Json(name="data")
	val data: List<DataItem?>? = null,

	@Json(name="status")
	val status: String? = null
)

data class DataItem(

	@Json(name="date")
	val date: String? = null,

	@Json(name="image")
	val image: String? = null,

	@Json(name="prediction_age")
	val prediction_age: String? = null,

	@Json(name="user_id")
	val user_id: Int? = null,

	@Json(name="prediction_result")
	val prediction_result: String? = null,

	@Json(name="prediction_score")
	val prediction_score: Any? = null,

	@Json(name="created_at")
	val createdAt: String? = null,

	@Json(name="id")
	val id: Int? = null
)
