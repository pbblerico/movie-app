package com.example.movieapp.fragments

import android.content.Context
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentLoggedInBinding
import com.example.movieapp.databinding.FragmentLoginBinding
import com.example.movieapp.fragments.authorization.SignUpFragment
import com.example.movieapp.viewModels.LogIn

class LoggedInFragment : Fragment(R.layout.fragment_logged_in), View.OnClickListener {
    private lateinit var binding: FragmentLoggedInBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var cont: Context
    private val TAG = "LOGIN"

    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoggedInBinding.inflate(inflater, container, false)

        bottomNav()

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
//        when(view) {
//
//            else -> changePage(SignUpFragment())
//        }
    }

    fun insertNestedFragment(childFragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction();
        transaction.replace(R.id.child_fragment_cont, childFragment).addToBackStack(null).commit();
    }

    private fun bottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.acc -> Toast.makeText(cont, "Account", Toast.LENGTH_SHORT).show()
                R.id.fav -> Toast.makeText(cont, "Favourite", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(cont, "Home", Toast.LENGTH_SHORT).show()
            }
//            replaceFragment(fragment
            true
        }
    }

}