package com.example.movieapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.databinding.FragmentLoggedInBinding
import com.example.movieapp.favouriteList.ui.FavouriteFragment
import com.example.movieapp.movieList.ui.MovieFragment

class LoggedInFragment : Fragment(R.layout.fragment_logged_in), View.OnClickListener {
    private lateinit var binding: FragmentLoggedInBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var cont: Context
    private val TAG = "LOGGED_IN"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoggedInBinding.inflate(inflater, container, false)

        bottomNav()
        insertNestedFragment()

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

    fun insertNestedFragment(childFragment: Fragment = MovieFragment()) {
        val transaction = childFragmentManager.beginTransaction();
        transaction.replace(R.id.child_fragment_cont, childFragment).addToBackStack(null).commit();
    }

    private fun bottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener{
            val fragment = when(it.itemId) {
                R.id.acc -> AccountFragment()
                R.id.fav -> FavouriteFragment()
                else -> MovieFragment()
            }
            insertNestedFragment(fragment)
            true
        }
    }

}