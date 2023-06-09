package com.example.movieapp.authorization.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.utils.Result
import com.example.movieapp.authorization.viewModel.LoginViewModel
import com.example.movieapp.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) , View.OnClickListener {
    private var binding: FragmentLoginBinding? = null
    private val TAG = "LOGIN"

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding!!.loginBtn.setOnClickListener(this)
        binding!!.signup.setOnClickListener(this)

        return binding!!.root
    }

    override fun onClick(view: View?) {
        when(view) {
            binding!!.loginBtn -> login()
            binding!!.signup -> Navigation.findNavController(view).navigate(R.id.loginToSignUpFragment)
        }
    }

    private fun clearFIelds() {
        binding!!.uname.setText("")
        binding!!.pass.setText("")
    }

    private fun login() {
        val email = binding!!.uname.text.toString().trim()
        val password = binding!!.pass.text.toString().trim()
        Navigation.findNavController(requireView()).navigate(R.id.toMovieFragment)

        viewModel.login(email, password)

        viewModel.loginStatus.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    (requireActivity() as MainActivity).showProgressBar()
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }
                is Result.Success -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    clearFIelds()
                    (requireActivity() as MainActivity).showBottomNavBar()
                    Navigation.findNavController(requireView()).navigate(R.id.toMovieFragment)
                }
                else -> {
                    (requireActivity() as MainActivity).hideProgressBar()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}