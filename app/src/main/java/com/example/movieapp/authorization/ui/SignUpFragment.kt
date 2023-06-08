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
import com.example.movieapp.databinding.FragmentSignUpBinding
import com.example.movieapp.utils.Result
import com.example.movieapp.authorization.viewModel.SignUpViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {
    private var binding: FragmentSignUpBinding? = null
    private val viewModel by viewModel<SignUpViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding!!.signupBtn.setOnClickListener(this)
        binding!!.login.setOnClickListener(this)

        return binding!!.root
    }

    private fun clearFields() {
        binding!!.name.setText("")
        binding!!.surname.setText("")
        binding!!.uname.setText("")
        binding!!.pass.setText("")
        binding!!.pass2.setText("")
    }

    private fun signup() {
        val name = (binding!!.name.text.toString()).trim()
        val surname = (binding!!.surname.text.toString()).trim()
        val email = (binding!!.uname.text.toString()).trim()
        val password1 = (binding!!.pass.text.toString()).trim()
        val password2 = (binding!!.pass2.text.toString()).trim()

        viewModel.signUp(name, surname, email, password1, password2)

        viewModel.signUpStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    (requireActivity() as MainActivity).showProgressBar()
                }
                is Result.Success -> {
                    clearFields()
                    (requireActivity() as MainActivity).hideProgressBar()
                    Navigation.findNavController(requireView()).navigate(R.id.signUpToLoginFragment)
                }
                else -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    (requireActivity() as MainActivity).hideProgressBar()
                }
            }
        }
    }
    override fun onClick(view: View?) {
        when(view) {
            binding!!.login -> Navigation.findNavController(requireView()).navigate(R.id.signUpToLoginFragment)
            else -> signup()
        }
    }
}