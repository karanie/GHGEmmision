package com.qaraniraka.myapplication.network

import com.qaraniraka.myapplication.model.FoobarList
import com.qaraniraka.myapplication.model.FoobarPostData
import com.qaraniraka.myapplication.model.ActivityPostData
import com.qaraniraka.myapplication.model.ActivityResults
import com.qaraniraka.myapplication.model.PostSuccess
import com.qaraniraka.myapplication.model.UserCheckEmailData
import com.qaraniraka.myapplication.model.UserData
import com.qaraniraka.myapplication.model.UserLoginPostData
import com.qaraniraka.myapplication.model.UserLogoutPostData
import com.qaraniraka.myapplication.model.UserRegistrationPostData
import com.qaraniraka.myapplication.model.VerifySuccess
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL =
    "http://10.0.2.2:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface GHGEmBackendApiService {
    @GET("api/foobar")
    suspend fun getFoobar(): FoobarList

    @POST("api/foobar")
    suspend fun postFoobar(@Body foobarPostData: FoobarPostData): PostSuccess

    @POST("api/user/register")
    suspend fun registerUser(@Body userRegistrationPostData: UserRegistrationPostData): PostSuccess

    @GET("api/user/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): UserCheckEmailData

    @GET("api/user/session/{session}")
    suspend fun getUserBySession(@Path("session") session: String): UserData

    @POST("api/user/verify")
    suspend fun loginUser(@Body userLoginPostData: UserLoginPostData): VerifySuccess

    @POST("api/user/logout")
    suspend fun logoutUser(@Body userLogoutPostData: UserLogoutPostData)

    @POST("api/activity")
    suspend fun postActivity(@Body postActivityData: ActivityPostData): PostSuccess

    @GET("api/activity/result/{activityId}")
    suspend fun getActivityResultById(@Path("activityId") activityId: Int): ActivityResults
}

object GHGEmBackendApi {
    val service: GHGEmBackendApiService by lazy {
        retrofit.create(GHGEmBackendApiService::class.java)
    }
}