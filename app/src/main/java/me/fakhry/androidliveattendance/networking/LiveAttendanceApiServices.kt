package me.fakhry.androidliveattendance.networking

import me.fakhry.androidliveattendance.model.AttendanceResponse
import me.fakhry.androidliveattendance.model.ForgotPasswordResponse
import me.fakhry.androidliveattendance.model.HistoryResponse
import me.fakhry.androidliveattendance.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LiveAttendanceApiServices {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/login")
    fun loginRequest(@Body body: String): Call<LoginResponse>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("auth/password/forgot")
    fun forgotPasswordRequest(@Body body: String): Call<ForgotPasswordResponse>

    @Multipart
    @Headers("Accept: application/json")
    @POST("attendance")
    fun attend(
        @Header("Authorization") token: String,
        @PartMap params: HashMap<String, RequestBody>,
        @Part photo: MultipartBody.Part
    ): Call<AttendanceResponse>

    @Headers("Accept: application/json")
    @GET("attendance/history")
    fun getHistoryAttendance(
        @Header("Authorization") token: String,
        @Query("from") fromDate: String,
        @Query("to") toDate: String
    ): Call<HistoryResponse>
}