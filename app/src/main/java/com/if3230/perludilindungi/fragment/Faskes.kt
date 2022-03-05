package com.if3230.perludilindungi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import com.if3230.perludilindungi.*
import com.if3230.perludilindungi.database.BookmarkedFaskesDatabase
import com.if3230.perludilindungi.databinding.FragmentFaskesBinding
import com.if3230.perludilindungi.recycler_view.FaskesAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [Faskes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Faskes : Fragment() {
	private lateinit var binding: FragmentFaskesBinding
	private lateinit var viewModel: MainViewModel
	private val adapter = FaskesAdapter(this)
	private lateinit var selectedProvince: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val dao1 = BookmarkedFaskesDatabase
			.getInstance(requireContext())
			.bookmarkedFaskesDao()
		val api1 = PerluDilindungiAPI.getInstance()
		viewModel =
			ViewModelProvider(this, MainViewModelFactory(MainRepository(api1), dao1))
				.get(MainViewModel::class.java)
		viewModel.getProvince()
		viewModel.province.observe(this) {
			val provinces = viewModel.province.value!!.results.map { it.key }
			val provinceArrAdapter =
				ArrayAdapter(requireContext(), R.layout.dropdown_item, provinces)
			binding.provincesSelectionAutocomplete.setAdapter(provinceArrAdapter)
		}
		viewModel.city.observe(this) {
			val cities = viewModel.city.value!!.results.map { it.key }
			val citiesArrAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, cities)
			binding.citiesSelectionAutocomplete.setAdapter(citiesArrAdapter)
		}
		viewModel.faskes.observe(this) {
			adapter.faskesList = it.data.toMutableList()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		binding = FragmentFaskesBinding.inflate(inflater, container, false)
		binding.provincesSelectionAutocomplete.onItemClickListener = ProvinceClickListener()
		binding.citiesSelectionAutocomplete.onItemClickListener = CityClickListener()
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.recyclerListFaskes?.adapter = adapter
		binding.recyclerListFaskes?.setHasFixedSize(true)
	}

	override fun onResume() {
		super.onResume()

		val act = requireActivity()
		val topBar: MaterialToolbar = act.findViewById(R.id.content_top_bar)!!
		topBar.title = act.getString(R.string.title_faskes_search_fragment)
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment FaskesSearch.
		 */
		@JvmStatic
		fun newInstance() =
			Faskes().apply { }
	}

	private inner class ProvinceClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
			val prov = parent.getItemAtPosition(pos).toString()
			selectedProvince = prov
			viewModel.getCity(prov)
		}
	}

	private inner class CityClickListener : AdapterView.OnItemClickListener {
		override fun onItemClick(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
			val city = parent.getItemAtPosition(pos).toString()
			viewModel.getFaskes(selectedProvince, city)
		}
	}
}