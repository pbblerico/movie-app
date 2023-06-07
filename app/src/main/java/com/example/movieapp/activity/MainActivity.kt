package com.example.movieapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

    fun hideProgressBar() {
        binding!!.prgBarMovies.visibility = View.GONE
    }

    fun showProgressBar() {
        binding!!.prgBarMovies.visibility = View.VISIBLE
    }

    fun showBottomNavBar() {
        binding!!.bottomNavigationView.visibility = View.VISIBLE

        val nav = findNavController(R.id.fragment)
        binding!!.bottomNavigationView.setupWithNavController(nav)
    }

    fun logout() {
        val fragmentManager = supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FirebaseAuth.getInstance().signOut()
    }
}