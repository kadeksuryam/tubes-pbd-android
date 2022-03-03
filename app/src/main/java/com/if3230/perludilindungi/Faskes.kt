package com.if3230.perludilindungi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.databinding.FragmentFaskesBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Faskes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Faskes : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

//	private var _binding: Faskes? = null
//	private val binding get() = _binding!!

//	private val viewModel by lazy { ViewModelProvider(requireActivity()).get(ContentViewModel::class.java) }

	private lateinit var binding: FragmentFaskesBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}

	}

//	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//		super.onViewCreated(view, savedInstanceState)
//
//		viewModel.updateActionBarTitle("Custom Title From Fragment")
//	}

	override fun onResume() {
		super.onResume()

		val provinsi = resources.getStringArray(R.array.provinsi)
		var arrayAdapterProvinsi = ArrayAdapter(requireContext(), R.layout.dropdown_item, provinsi)
		binding.autoCompleteTextView.setAdapter(arrayAdapterProvinsi)

		val kota = resources.getStringArray(R.array.kota)
		var arrayAdapterKota = ArrayAdapter(requireContext(), R.layout.dropdown_item, kota)
		binding.autoCompleteTextView6.setAdapter(arrayAdapterKota)

		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_faskes_search_fragment)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = FragmentFaskesBinding.inflate(inflater, container, false)


		return binding.root
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment FaskesSearch.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String, param2: String) =
			Faskes().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1, param1)
					putString(ARG_PARAM2, param2)
				}
			}
	}
}