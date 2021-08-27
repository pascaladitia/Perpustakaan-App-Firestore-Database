package com.examplel.perpustakaanapp.ui.home.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.examplel.perpustakaanapp.R
import com.examplel.perpustakaanapp.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment(), View.OnClickListener {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentUserBinding

    private var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.fragHomeLogout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fragHome_logout -> {
                AlertDialog.Builder(context).apply {
                    setTitle("Logout")
                    setMessage("Anda yakin ingin logout?")
                    setCancelable(false)

                    setPositiveButton("Yes") { dialogInterface, i ->

                        auth?.signOut()
                        navController.navigate(R.id.action_userFragment_to_loginActivity2)
                        activity?.finish()
                    }

                    setNegativeButton("Cancel") { dialogInterface, i ->
                        dialogInterface?.dismiss()
                    }
                }.show()
            }
        }
    }
}