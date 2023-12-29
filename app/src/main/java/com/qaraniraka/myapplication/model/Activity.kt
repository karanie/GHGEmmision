package com.qaraniraka.myapplication.model

import com.google.gson.annotations.SerializedName

data class ActivityBerkendaraDetail(
    @SerializedName("engine_size")
    val engineSize: Double,
    val cylinders: Int,
    @SerializedName("fuel_consumption")
    val fuelConsumption: Double,
    @SerializedName("fuel_type")
    val fuelType: String,
    @SerializedName("travel_distance")
    val travelDistance: Double
)

data class ActivityMakanDetail(
    @SerializedName("food_product")
    val foodProduct: String,
    @SerializedName("food_weight")
    val foodWeight: Double
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

data class ActivityHistoryData(
    @SerializedName("activity_type")
    val activityType: String,
    @SerializedName("activity_detail")
    val activityDetail: String,
    val emission: Double,
    val message: String,
    val ctime: String
)

data class ActivityHistory(
    val data: List<ActivityHistoryData>,
    val totalRecordsCount: Int
)