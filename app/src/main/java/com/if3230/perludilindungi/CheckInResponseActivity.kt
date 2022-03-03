package com.if3230.perludilindungi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.if3230.perludilindungi.Model.UserStatus
import com.if3230.perludilindungi.databinding.ActivityCheckInStatusBinding

class CheckInResponseActivity : AppCompatActivity() {
	private var userStatus: String? = ""

	private lateinit var binding: ActivityCheckInStatusBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCheckInStatusBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		userStatus = intent.getStringExtra("userStatus")
		val reason = intent.getStringExtra("reason")

		setSupportActionBar(binding.checkInAppBar)
		val userStatusView = binding.userCheckInStatusText
		val reasonView = binding.userCheckInReasonText
		val userStatusImage = binding.checkInStatusImage
		val appBar = binding.checkInAppBar


		reasonView.text = reason

		if (userStatus.equals(UserStatus.green.toString())) {
			userStatusView.text = resources.getString(R.string.check_in_success)

			userStatusImage.setImageResource(R.drawable.ic_baseline_check_24)
			userStatusImage.setBackgroundColor(
				ContextCompat.getColor(this, R.color.check_in_green)
			)

			appBar.setNavigationOnClickListener {
				val intent = Intent(this, Content::class.java)
				startActivity(intent)
			}
		} else {
			userStatusView.text = resources.getString(R.string.check_in_fail)
			userStatusImage.setImageResource(R.drawable.ic_baseline_clear_24)

			when (userStatus) {
				UserStatus.red.toString() -> userStatusImage.setBackgroundColor(
					ContextCompat.getColor(this, R.color.check_in_red)
				)
				UserStatus.yellow.toString() -> userStatusImage.setBackgroundColor(
					ContextCompat.getColor(this, R.color.check_in_yellow)
				)
				else -> userStatusImage.setBackgroundColor(
					ContextCompat.getColor(this, R.color.check_in_black)
				)
			}

			appBar.setNavigationOnClickListener {
				val intent = Intent(this, CheckIn::class.java)
				startActivity(intent)
			}
		}
	}

	override fun onBackPressed() {
		super.onBackPressed()
		val intent: Intent = if (userStatus.equals(UserStatus.green.toString())) {
			Intent(this, Content::class.java)
		} else {
			Intent(this, CheckIn::class.java)
		}
		startActivity(intent)
	}
}