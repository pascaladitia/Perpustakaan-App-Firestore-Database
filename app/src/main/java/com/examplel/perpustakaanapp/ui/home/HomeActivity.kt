package com.examplel.perpustakaanapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.examplel.perpustakaanapp.R
import com.examplel.perpustakaanapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navController = Navigation.findNavController(this, R.id.nav_host_home)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }
}