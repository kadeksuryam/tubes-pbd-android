package com.if3230.perludilindungi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class Content : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_content)

		val topAppBar = findViewById<MaterialToolbar>(R.id.content_top_bar)
		topAppBar.setNavigationOnClickListener { v ->
			// TODO: Finish the listener
			Toast.makeText(applicationContext, "navigation icon pressed", Toast.LENGTH_SHORT).show()
		}

		val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		bottomNavigationView.setupWithNavController(navController)
	}
}