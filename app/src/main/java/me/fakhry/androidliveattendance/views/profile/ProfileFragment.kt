package me.fakhry.androidliveattendance.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import me.fakhry.androidliveattendance.databinding.FragmentProfileBinding
import me.fakhry.androidliveattendance.views.changepass.ChangePasswordActivity
import me.fakhry.androidliveattendance.views.login.LoginActivity
import me.fakhry.androidliveattendance.views.main.MainActivity

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding?.btnChangePassword?.setOnClickListener {
            context?.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }

        binding?.btnChangeLanguage?.setOnClickListener {
            val toast = Toast.makeText(context, "Change Language", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding?.btnLogout?.setOnClickListener {
            context?.startActivity(Intent(context, LoginActivity::class.java))
            (activity as MainActivity).finishAffinity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}