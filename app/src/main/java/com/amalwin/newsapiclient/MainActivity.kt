package com.amalwin.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.amalwin.newsapiclient.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!
            .findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}