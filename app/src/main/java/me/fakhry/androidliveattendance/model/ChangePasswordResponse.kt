package me.fakhry.androidliveattendance.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

    @field:SerializedName("message")
    val message: String? = null
)