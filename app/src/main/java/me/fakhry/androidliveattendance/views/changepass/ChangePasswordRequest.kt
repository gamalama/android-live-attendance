package me.fakhry.androidliveattendance.views.changepass

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(

    @field:SerializedName("password_old")
    val passwordOld: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("password_confirmation")
    val passwordConfirmation: String? = null
)
