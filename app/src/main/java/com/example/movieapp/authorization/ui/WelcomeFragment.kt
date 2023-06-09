package com.example.movieapp.authorization.ui

import android.os.Bundle
import android.provider.SyncStateContract.Constants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome), View.OnClickListener {
    private var binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding!!.loginBtn.setOnClickListener(this)
        binding!!.signupBtn.setOnClickListener(this)

        return binding!!.root
    }

    override fun onClick(view: View?) {
        when(view) {
            binding!!.loginBtn -> {
                if(com.example.movieapp.utils.Constants.auth.currentUser != null) {
                    (requireActivity() as MainActivity).showBottomNavBar()
                    Navigation.findNavController(view).navigate(R.id.welcomeToMovieFragment)
                    return
                }
                Navigation.findNavController(view).navigate(R.id.toLoginFragment)
            }
            binding!!.signupBtn -> Navigation.findNavController(view).navigate(R.id.toSignUpFragment)
        }
    }
}