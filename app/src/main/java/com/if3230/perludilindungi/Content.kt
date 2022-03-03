package com.if3230.perludilindungi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.if3230.perludilindungi.databinding.ActivityContentBinding

class Content : AppCompatActivity() {
	private lateinit var binding: ActivityContentBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityContentBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val topAppBar = binding.contentTopBar
		topAppBar.setNavigationOnClickListener { _ ->
			// TODO: Finish the listener
			Toast.makeText(applicationContext, "navigation icon pressed", Toast.LENGTH_SHORT).show()
		}

		val bottomNavigationView = binding.bottomNavigationView
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		bottomNavigationView.setupWithNavController(navController)
	}
}