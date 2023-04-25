package com.example.movieapp.fragments.authorization

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.fragments.LoggedInFragment
import com.example.movieapp.viewModels.LogIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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