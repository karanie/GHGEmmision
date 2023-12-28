package com.qaraniraka.myapplication.model

import com.google.gson.annotations.SerializedName

data class ActivityBerkendaraDetail(
    val engineSize: Double,
    val cylinders: Int,
    val fuelConsumption: Double,
    val fuelType: String,
    val travelDistance: Double
)

data class ActivityPostData(
    val session: String,
    @SerializedName("activity_type")
    val activityType: String,
    @SerializedName("activity_detail")
    val activityDetail: String
)

data class ActivityResults(
    val emission: Double,
    val message: String
)