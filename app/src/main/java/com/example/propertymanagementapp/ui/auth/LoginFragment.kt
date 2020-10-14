package com.example.propertymanagementapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementapp.R
import com.example.propertymanagementapp.data.model.LoginResponse
import com.example.propertymanagementapp.databinding.FragmentLoginBinding
import com.example.propertymanagementapp.helpers.SessionManager
import com.example.propertymanagementapp.helpers.d
import com.example.propertymanagementapp.helpers.toast
import com.example.propertymanagementapp.ui.home.MainActivity

private const val ARG_PARAM1 = "param1"

class LoginFragment : Fragment(), LoginListener {

    lateinit var sessionManager: SessionManager
    lateinit var mBinding: FragmentLoginBinding

    private var mode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        val viewModel: AuthViewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.setAuthMode(mode!!)
        mBinding.viewModel = viewModel
        viewModel.loginListener = this
        sessionManager = SessionManager(mBinding.root.context)
        return mBinding.root
    }


    override fun onStarted() {
        mBinding.root.context.toast("onStarted")
        mBinding.root.context.d("Logging")
    }

    override fun onSuccess(response: LiveData<LoginResponse>) {
        response.observe(this, Observer {
            sessionManager.saveUserLogin(response.value!!)
            mBinding.root.context.toast("Logged In")
            mBinding.root.context.startActivity(Intent(mBinding.root.context, MainActivity::class.java))
        })
    }

    override fun failure(message: String) {
        mBinding.root.context.toast("Message")
    }

    companion object {
        @JvmStatic
        fun newInstance(mode: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, mode)
                }
            }
    }

}