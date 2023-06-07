package com.example.movieapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.activity.MainActivity
import com.example.movieapp.adapter.FavAdapter
import com.example.movieapp.databinding.FragmentAccountBinding
import com.example.movieapp.databinding.FragmentFavouriteBinding
import com.example.movieapp.authorization.WelcomeFragment
import com.example.movieapp.models.MovieListResponse
import com.example.movieapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AccountFragment : Fragment(R.layout.fragment_account) {
   private lateinit var binding: FragmentAccountBinding

   private lateinit var mainActivity: MainActivity
   private lateinit var cont: Context
   private val TAG = "ACC"

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = FragmentAccountBinding.inflate(inflater, container, false)

      loadInfo()

      binding.logout.setOnClickListener{
         mainActivity.logout()
         mainActivity.replaceFragment(WelcomeFragment())
      }
      return binding.root
   }

   private fun loadInfo() {
      val ref = FirebaseDatabase.getInstance().getReference("Users")
      ref.addListenerForSingleValueEvent(object : ValueEventListener {
         override fun onDataChange(snapshot: DataSnapshot) {
            for (ds in snapshot.children) {
               if(FirebaseAuth.getInstance().uid!! == ds.child("uid").value) {
                  binding.usersname.text = ds.child("name").value.toString()
                  binding.surname.text = ds.child("surname").value.toString()
                  binding.mail.text = ds.child("email").value.toString()
                  binding.userid.text = ds.child("uid").value.toString()
               }
            }
         }

         override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
         }

      })
   }

   override fun onAttach(context: Context) {
      mainActivity = activity as MainActivity
      cont = context
      super.onAttach(context)
   }
}