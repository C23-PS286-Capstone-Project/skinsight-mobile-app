package com.dayatmuhammad.skinsight.data

import com.squareup.moshi.Json

data class PredictResponse(

	@Json(name="data")
	val data: DataPredict? = null,

	@Json(name="message")
	val message: String? = null,

	@Json(name="status")
	val status: String? = null
)

data class DataPredict(

	@Json(name="image")
	val image: String? = null,

	@Json(name="prediction_age")
	val prediction_age: String? = null,

	@Json(name="user_id")
	val userId: Int? = null,

	@Json(name="prediction_result")
	val prediction_result: String? = null,

	@Json(name="prediction_score")
	val prediction_score: Any? = null
)
