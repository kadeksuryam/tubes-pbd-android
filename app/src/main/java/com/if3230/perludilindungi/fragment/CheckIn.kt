package com.if3230.perludilindungi.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.if3230.perludilindungi.CheckInActivity
import com.if3230.perludilindungi.R
import com.if3230.perludilindungi.databinding.FragmentCheckInBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CheckIn.newInstance] factory method to
 * create an instance of this fragment.
 */
class CheckIn : Fragment() {
	private lateinit var binding: FragmentCheckInBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val intent = Intent(activity, CheckInActivity::class.java)
		activity?.startActivity(intent)
	}


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_check_in, container, false)
	}

	override fun onResume() {
		super.onResume()
		val nav = this.findNavController()
		nav.navigate(R.id.news)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment CheckIn.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance() =
			CheckIn().apply {
				arguments = Bundle().apply { }
			}
	}
}