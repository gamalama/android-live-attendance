package me.fakhry.androidliveattendance.views.changepass

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import me.fakhry.androidliveattendance.R
import me.fakhry.androidliveattendance.databinding.ActivityChangePasswordBinding
import me.fakhry.androidliveattendance.dialog.MyDialog
import me.fakhry.androidliveattendance.hawkstorage.HawkStorage
import me.fakhry.androidliveattendance.model.ChangePasswordResponse
import me.fakhry.androidliveattendance.model.LoginResponse
import me.fakhry.androidliveattendance.networking.ApiServices
import me.fakhry.androidliveattendance.networking.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

class ChangePasswordActivity : AppCompatActivity() {

    companion object {
        private val TAG = ChangePasswordActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {
        setSupportActionBar(binding.tbChangePassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onClick() {
        binding.tbChangePassword.setNavigationOnClickListener {
            finish()
        }

        binding.btnChangePassword.setOnClickListener {
            val oldPass = binding.etOldPassword.text.toString()
            val newPass = binding.etNewPassword.text.toString()
            val confirmNewPass = binding.etConfirmNewPassword.text.toString()

            if (checkValidation(oldPass, newPass, confirmNewPass)) {
                changePassToServer(oldPass, newPass, confirmNewPass)
            }
        }
    }

    private fun changePassToServer(oldPass: String, newPass: String, confirmNewPass: String) {
        val token = HawkStorage.instance(this).getToken()
        val changePasswordRequest = ChangePasswordRequest(
            passwordOld = oldPass,
            password = newPass,
            passwordConfirmation = confirmNewPass
        )
        val changePasswordRequestString = Gson().toJson(changePasswordRequest)
        MyDialog.showProgressDialog(this)
        ApiServices.getLiveAttendanceServices()
            .changePassword("Bearer $token", changePasswordRequestString)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    MyDialog.hideDialog()
                    if (response.isSuccessful) {
                        MyDialog.dynamicDialog(
                            this@ChangePasswordActivity,
                            getString(R.string.success),
                            getString(R.string.your_password_has_been_update)
                        )
                        Handler(Looper.getMainLooper()).postDelayed({
                            MyDialog.hideDialog()
                            finish()
                        }, 2000)
                    } else {
                        val errorConverter: Converter<ResponseBody, ChangePasswordResponse> =
                            RetrofitClient
                                .getClient()
                                .responseBodyConverter(
                                    LoginResponse::class.java,
                                    arrayOfNulls<Annotation>(0)
                                )
                        var errorResponse: ChangePasswordResponse?
                        try {
                            response.errorBody()?.let {
                                errorResponse = errorConverter.convert(it)

                                MyDialog.dynamicDialog(
                                    this@ChangePasswordActivity,
                                    getString(R.string.failed),
                                    errorResponse?.message.toString()
                                )
                            }
                        } catch (e: IOException) {
                            Log.e(TAG, "Error: ${e.message}")
                        }
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    MyDialog.hideDialog()
                    MyDialog.dynamicDialog(
                        this@ChangePasswordActivity,
                        getString(R.string.alert),
                        "Error: ${t.message}"
                    )
                }
            })
    }

    private fun checkValidation(oldPass: String, newPass: String, confirmNewPass: String): Boolean {
        if (oldPass.isEmpty()) {
            binding.etOldPassword.error = getString(R.string.please_field_your_password)
            binding.etOldPassword.requestFocus()
        } else if (newPass.isEmpty()) {
            binding.etNewPassword.error = getString(R.string.please_field_your_password)
            binding.etNewPassword.requestFocus()
        } else if (confirmNewPass.isEmpty()) {
            binding.etConfirmNewPassword.error = getString(R.string.please_field_your_password)
            binding.etConfirmNewPassword.requestFocus()
        } else if (newPass != confirmNewPass) {
            binding.etNewPassword.error = getString(R.string.your_password_didnt_match)
            binding.etNewPassword.requestFocus()
            binding.etConfirmNewPassword.error = getString(R.string.your_password_didnt_match)
            binding.etConfirmNewPassword.requestFocus()
        } else {
            binding.etNewPassword.error = null
            binding.etConfirmNewPassword.error = null
            return true
        }
        return false
    }
}