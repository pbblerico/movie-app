package com.example.movieapp.authorization

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.authorization.ui.LoginFragment
import com.example.movieapp.authorization.ui.SignUpFragment
import com.example.movieapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome), View.OnClickListener {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener(this)
        binding.signupBtn.setOnClickListener(this)

        return binding.root
    }

    override fun onAttach(context: Context) {
        mainActivity = activity as MainActivity
        super.onAttach(context)
    }

    override fun onClick(view: View?) {
        val fragment = when(view) {
            binding.loginBtn -> LoginFragment()
            else -> SignUpFragment()
        }
        mainActivity.replaceFragment(fragment)
    }
}