package com.example.movieapp.authorization.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentSignUpBinding
import com.example.movieapp.fragments.LoggedInFragment
import com.example.movieapp.authorization.viewModel.SignUp

class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var cont: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signupBtn.setOnClickListener(this)
        binding.login.setOnClickListener(this)

        return binding.root
    }

    override fun onAttach(context: Context) {
        mainActivity = activity as MainActivity
        cont = context
        super.onAttach(context)
    }

    private fun signup() {
        val name = (binding.name.text.toString()).trim()
        val surname = (binding.surname.text.toString()).trim()
        val email = (binding.uname.text.toString()).trim()
        val password1 = (binding.pass.text.toString()).trim()
        val password2 = (binding.pass2.text.toString()).trim()

        if(SignUp.signup(name, surname, email, password1, password2, cont)) {
            binding.name.setText("")
            binding.surname.setText("")
            binding.uname.setText("")
            binding.pass.setText("")
            binding.pass2.setText("")


            changePage(LoggedInFragment())
        }
    }

    private fun changePage(fragment: Fragment) {
        mainActivity.replaceFragment(fragment)
    }

    override fun onClick(view: View?) {
        when(view) {
            binding.login -> changePage(LoginFragment())
            else -> signup()
        }
    }
}