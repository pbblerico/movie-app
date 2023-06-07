package com.example.movieapp.authorization.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.authorization.viewModel.LogIn
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.fragments.LoggedInFragment

class LoginFragment : Fragment(R.layout.fragment_login) , View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var cont: Context
    private val TAG = "LOGIN"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener(this)
        binding.signup.setOnClickListener(this)
//        mainActivity.logout()

        return binding.root
    }

    override fun onAttach(context: Context) {
        mainActivity = activity as MainActivity
        cont = context
        super.onAttach(context)
    }

    private fun changePage(fragment: Fragment) {
        mainActivity.replaceFragment(fragment)
    }


    override fun onClick(view: View?) {
        when(view) {
            binding.loginBtn -> login()
            else -> changePage(SignUpFragment())
        }
    }

    private fun login() {
        var email = binding.uname.text.toString().trim()
        var password = binding.pass.text.toString().trim()

        if(LogIn.login(email, password, cont)) {
            binding.uname.setText("")
            binding.pass.setText("")

            changePage(LoggedInFragment())
        }
    }
}